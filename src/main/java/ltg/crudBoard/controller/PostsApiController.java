package ltg.crudBoard.controller;


import lombok.RequiredArgsConstructor;
import ltg.crudBoard.auth.LoginUser;
import ltg.crudBoard.dto.PostsResponseDto;
import ltg.crudBoard.dto.PostsSaveRequestDto;
import ltg.crudBoard.dto.PostsUpdateRequestDto;
import ltg.crudBoard.dto.UserSessionDto;
import ltg.crudBoard.service.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostsApiController
{

    private final PostsService postsService;

    //id에 맞는 dto 반환
    @GetMapping("/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id)
    {
        return postsService.findById(id);
    }

    //create
    @PostMapping("/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto, @LoginUser UserSessionDto userSessionDto)
    {
        //닉네임도 넘겨준다
        return postsService.save(userSessionDto.getNickname(),requestDto);
    }

    //update
    @PutMapping("/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto)
    {
        return postsService.update(id, requestDto);
    }

    //delete
    @DeleteMapping("/posts/{id}")
    public Long delete(@PathVariable Long id)
    {
        postsService.delete(id);
        return id;
    }





}
