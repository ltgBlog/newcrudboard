package ltg.crudBoard.config;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.auth.CustomUserDetailsService;
import ltg.crudBoard.oauth.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //권한이나 인증 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationFailureHandler LoginFailureHandler; //로그인 실패 핸들러
    private final CustomOAuth2UserService customOAuth2UserService; //oauth

    @Bean
    public BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }

    //패스워드 암호화
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder());
    }

    //누구에게나 아래 링크는 열어둔다
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**");
    }


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable()
                //ignoringAntMatchers("/api/**")
                .authorizeRequests()//url별 권한 접근 제어 관리 옵션 시작
                //.headers().frameOptions().disable()// h2콘솔 사용하기 위해  disable.
                .antMatchers("/", "/auth/**", "/posts/read/**").permitAll() //권한 관리 대상 지정. permitall-모든 권한에게 공개
                //.antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() //나머지 요청들은 인증된 사람에게만 공개
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/loginProc")
                .failureHandler(LoginFailureHandler)
                .defaultSuccessUrl("/")
                .and()
                .logout() //기본 경로는 /logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .and()//oauth
                .oauth2Login()
                .userInfoEndpoint()//로그인 성공 후 사용자 정보를 가져올 때의 설정들을 담당
                .userService(customOAuth2UserService);//로그인 성공 시 후속 조치를 진행 할 UserService 인터페이스의 구현체를 등록

    }

}
