package ltg.crudBoard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ltg.crudBoard.domain.Role;
import ltg.crudBoard.domain.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //3개 같이 쓰면 에러 방지?
public class UserSaveRequestDto
{
    private String userid;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    //dto -> entity. db에 저장.
    public User toEntity()
    {
        User user = User.builder()
                .userid(userid)
                .password(password)
                .nickname(nickname)
                .email(email)
                .role(role)
                .build();
        return user;
    }

}
