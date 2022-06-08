package ltg.crudBoard.controller;


import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//화면 연결 컨트롤러

@Controller
@RequiredArgsConstructor
public class UserIndexController
{

    private final UserService userService;

    @GetMapping("/auth/join")
    public String join()
    {
        return "/user/user_join";
    }

    @PostMapping("/auth/joinProc")
    public String joinProc(UserSaveRequestDto dto)
    {
        userService.join(dto);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String login()
    {
        return "/user/user_login";
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
