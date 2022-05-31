package ltg.crudBoard.dto;

import lombok.Getter;
import ltg.crudBoard.domain.Posts;

@Getter
public class PostsListResponseDto
{
    private Long id;
    private String title;
    private String writer;
    private String modifiedDate;

    public PostsListResponseDto(Posts entity)
    {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.modifiedDate = entity.getModifiedDate();
    }
}