package ltg.crudBoard.controller;


import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.service.UserService;
import ltg.crudBoard.validator.EmailValidator;
import ltg.crudBoard.validator.NicknameValidator;
import ltg.crudBoard.validator.UsernameValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;


//화면 연결 컨트롤러

@Controller
@RequiredArgsConstructor
public class UserIndexController
{
    private final UsernameValidator usernameValidator;
    private final NicknameValidator nicknameValidator;
    private final EmailValidator emailValidator;

    private final UserService userService;

    //커스텀 유효성 검증을 위해 바인딩
    @InitBinder  //특정 컨트롤러에서 바인딩 설정을 할 때 쓰임 @Controller나 @ControllerAdvice가 붙은 클래스는 @InitBinder가 붙은 메소드를 가질 수 있다.
    public void validatorBinder(WebDataBinder binder)
    {
        binder.addValidators(usernameValidator);
        binder.addValidators(nicknameValidator);
        binder.addValidators(emailValidator);

    }

    @GetMapping("/auth/join")
    public String join()
    {
        return "user/user_join";

        //return "user/user_join";
    }

    @PostMapping("/auth/joinProc") //@ModelAttribute 생략됨.
    public String joinProc(@Valid UserSaveRequestDto userDto, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            //가입 실패 시 데이터 값 유지
            model.addAttribute("userDto", userDto);

            //에러 필드, 메시지 핸들링
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);
            for(String key : validatorResult.keySet())
            {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "user/user_join";
            //return "user/user_join";

        }
        userService.join(userDto);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model)
    {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/user_login";
        //return "user/user_login";
    }

/*
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
        {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
*/
}
