package ltg.crudBoard.auth;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.domain.User;
import ltg.crudBoard.dto.UserSessionDto;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    private final HttpSession session;

    //userid가 db에 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + username));
        session.setAttribute("user", new UserSessionDto(user));

        //security 세션에 정보 저장
        return new CustomUserDetails(user);

    }

}





























