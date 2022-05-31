package ltg.crudBoard.controller;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.PostsResponseDto;
import ltg.crudBoard.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


//화면단 까지 불러줌 로직 처리는 apicontroller가
@RequiredArgsConstructor
@Controller
public class IndexController
{
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/write")
    public String postsSave()
    {
        return "posts/posts_write";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model)
    {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts/posts_update";
    }
}
