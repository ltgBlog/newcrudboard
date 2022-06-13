package ltg.crudBoard.controller;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.auth.LoginUser;
import ltg.crudBoard.domain.Posts;
import ltg.crudBoard.dto.PostsResponseDto;
import ltg.crudBoard.dto.UserSessionDto;
import ltg.crudBoard.service.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


//화면단 까지 불러줌 로직 처리는 PostsApiController가..
@RequiredArgsConstructor
@Controller
public class IndexController
{
    private final PostsService postsService;
    private final HttpSession httpSession;
    //처음화면
    @GetMapping("/")
    public String index(Model model, @LoginUser UserSessionDto user,
                        @PageableDefault(page=0, size=10, sort = "id", direction = Sort.Direction.DESC)
                        Pageable pageable, String searchKeyword)
    {
        Page<Posts> list = null;
        if(searchKeyword == null) //검색어가 없다면
        {
            list=postsService.paging(pageable);
        }
        else
        {
            list=postsService.search(searchKeyword, pageable);
        }

        //UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        if (user != null) { //로그인 되었다면 model에 저장
            model.addAttribute("user", user.getNickname());
        }

        //현재 페이지 기준, 앞 뒤로 4칸씩 보여주기 위함
        //페이지는 0부터 시작하고, 최대,최소를 넘어가지 않기 위해 max,min 메소드를 사용했다.
        int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
        int endPage = Math.min(list.getPageable().getPageNumber()+4, list.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("posts", list); //게시글 리스트

        return "index";
    }

    //글 쓰기 페이지로!
    @GetMapping("/posts/write")
    public String postsSave(Model model, @LoginUser UserSessionDto user)
    {
        //UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user.getNickname());
        }
        return "posts/posts_write";
    }

    //글 수정 페이지로!
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser UserSessionDto user)
    {
        //UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user.getNickname());
        }

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);

        return "posts/posts_update";
    }

    //글 상세보기 페이지로!
    @GetMapping("/posts/read/{id}")
    public String postsRead(@PathVariable Long id, Model model, @LoginUser UserSessionDto user)
    {
        PostsResponseDto dto = postsService.findById(id);
        //UserSessionDto user = (UserSessionDto) httpSession.getAttribute("user");
        if (user != null)
        {
            model.addAttribute("user", user.getNickname());

            //게시글 작성자가 본인인지 확인
            if(dto.getUserId().equals(user.getId()))
            {
                model.addAttribute("sameUser", true);
            }

        }
        //조회수 증가
        postsService.updateHit(id);
        model.addAttribute("posts", dto);

        return "posts/posts_read";
    }
}
