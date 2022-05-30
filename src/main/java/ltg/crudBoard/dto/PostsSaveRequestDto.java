package ltg.crudBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ltg.crudBoard.domain.Posts;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto
{
    private String title;
    private String content;
    private String writer;

    @Builder
    public PostsSaveRequestDto(String title,String content,String writer)
    {
        this.title=title;
        this.content = content;
        this.writer = writer;
    }

    public Posts toEntity()
    {
        return Posts.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

}