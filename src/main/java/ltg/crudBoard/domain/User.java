package ltg.crudBoard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 회원정보 수정
    //소셜 로그인 정보가 변경되었을 때를 대비(구글 이름이 변경되면 업데이트 한다.)
    //db에도 반영
    public User update(String nickname)
    {
        this.nickname = nickname;
        return this;
    }

    //oauth 로그인 시 이미 등록된 회원이면 수정날짜만 업데이트
    //기존 데이터는 그대로 유지
    public User updateModifiedDate()
    {
        this.onPreUpdate();
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}


