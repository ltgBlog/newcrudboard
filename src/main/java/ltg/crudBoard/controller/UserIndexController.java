package ltg.crudBoard.controller;


import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


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

}
