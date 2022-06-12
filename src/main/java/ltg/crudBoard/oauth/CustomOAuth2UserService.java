package ltg.crudBoard.oauth;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.domain.User;
import ltg.crudBoard.dto.UserSessionDto;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>
{
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();//로그인 진행중인 서비스 구분(구글인지 네이버인지)

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();//OAuth2 로그인 진행 시 키가 되는 필드값. primary key.

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new UserSessionDto(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    //소셜 로그인 시 바뀐 내용 반영
    private User saveOrUpdate(OAuthAttributes attributes)
    {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(User::updateModifiedDate)//로그인 시 날짜 업데이트
                .map(entity -> entity.update(attributes.getNickname())) //구글에서 이름을 바꾸고 다시 로그인 하면 바뀐 이름 반영
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}