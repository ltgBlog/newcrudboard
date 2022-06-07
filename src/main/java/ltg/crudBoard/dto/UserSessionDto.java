package ltg.crudBoard.dto;

import lombok.Getter;
import ltg.crudBoard.domain.Role;
import ltg.crudBoard.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable
{

    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;
    //entity -> dto. 조회
    public UserSessionDto(User user)
    {

        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
