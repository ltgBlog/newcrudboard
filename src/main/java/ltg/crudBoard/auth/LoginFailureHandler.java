package ltg.crudBoard.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler
{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException
    {
        String errorMessage;
        if(exception instanceof BadCredentialsException)
        {
            errorMessage = "아이디 또는 비밀번호가 틀립니다.";
        }
        else if(exception instanceof InternalAuthenticationServiceException)
        {
            errorMessage = "내부적인 문제가 발생하였습니다. 잠시 후 다시 시도해주세요";
        }
        else if(exception instanceof UsernameNotFoundException)
        {
            errorMessage = "존재하지 않는 계정입니다.";
        }
        else
        {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 잠시 후 다시 시도해주세요";
        }

        errorMessage= URLEncoder.encode(errorMessage,"UTF-8");
        setDefaultFailureUrl("/auth/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request,response,exception);


    }

}
