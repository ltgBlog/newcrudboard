package ltg.crudBoard.controller;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.PostsResponseDto;
import ltg.crudBoard.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


//화면단(머스태치) 까지 불러줌 로직 처리는 PostsApiController가..
@RequiredArgsConstructor
@Controller
public class IndexController
{
    private final PostsService postsService;

    //처음화면
    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("posts", postsService.findAllDesc()); //게시글 리스트

        return "index";
    }

    //글 쓰기 페이지로!
    @GetMapping("/posts/write")
    public String postsSave()
    {
        return "posts/posts_write";
    }

    //글 수정 페이지로!
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model)
    {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);

        return "posts/posts_update";
    }

    //글 상세보기 페이지로!
    @GetMapping("/posts/read/{id}")
    public String postsRead(@PathVariable Long id, Model model)
    {
        PostsResponseDto dto = postsService.findById(id);
        postsService.updateHit(id);
        model.addAttribute("posts", dto);


        return "posts/posts_read";
    }
}
