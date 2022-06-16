package ltg.crudBoard.controller;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.auth.LoginUser;
import ltg.crudBoard.dto.CommentRequestDto;
import ltg.crudBoard.dto.PostsUpdateRequestDto;
import ltg.crudBoard.dto.UserSessionDto;
import ltg.crudBoard.service.CommentsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController //ResponseBody + Controller. ResponseBody 사용 시 jackson 라이브러리 사용
public class CommentsApiController
{
    private final CommentsService commentsService;

    //create
    @PostMapping("/posts/{id}/comments")
    public Long commentSave(@PathVariable Long id,
                            @RequestBody CommentRequestDto dto,
                            @LoginUser UserSessionDto userSessionDto)
    {
        return commentsService.commentSave(userSessionDto.getNickname(), id, dto);
    }

    //update
    @PutMapping("/posts/{postsId}/comments/{id}")
    public void update(@PathVariable Long id, @RequestBody CommentRequestDto requestDto)
    {
        commentsService.update(id, requestDto);
    }

    //delete
    @DeleteMapping("/posts/{postsId}/comments/{id}")
    public void delete(@PathVariable Long id)
    {
        commentsService.delete(id);
    }
}
