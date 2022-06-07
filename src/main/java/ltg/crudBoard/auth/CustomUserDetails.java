package ltg.crudBoard.auth;

import lombok.AllArgsConstructor;
import ltg.crudBoard.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails
{
    private final User user;

    /**
     * 해당 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(()->"ROLE_" + user.getRole());

        return authorities;
    }

    /**
     * 비밀번호
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    /**
     * PK값
     */
    @Override
    public String getUsername()
    {
        return user.getUsername();
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
    @Override
    public boolean isEnabled() {
        //이메일이 인증되어 있고 계정이 잠겨있지 않으면 true
        return true;
    }

}