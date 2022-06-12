package ltg.crudBoard.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ltg.crudBoard.domain.Role;
import ltg.crudBoard.domain.User;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OAuthAttributes
{
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String nickname;
    private String email;
    private Role role;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes)
    {

        /*
        if("naver".equals(registrationId))
        {
            return ofNaver("id", attributes);
        }
*/
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .username((String) attributes.get("email"))
                .email((String) attributes.get("email"))
                .nickname((String) attributes.get("name"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /*
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
*/
    public User toEntity() {
        return User.builder()
                .username(email)
                .email(email)
                .nickname(nickname)
                .role(Role.SOCIAL) //원래는 GUEST임!
                .build();
    }

}
