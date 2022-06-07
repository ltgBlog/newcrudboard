package ltg.crudBoard.config;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.auth.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder());
    }

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
                .defaultSuccessUrl("/")
                .and()
                .logout() //기본 경로는 /logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }

}
