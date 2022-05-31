package ltg.crudBoard.dto;

import lombok.Getter;
import ltg.crudBoard.domain.Posts;


 //수정 조회?
@Getter
public class PostsResponseDto
{
    private Long id;
    private String title;
    private String content;
    private String writer;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
    }
}