package ltg.crudBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ltg.crudBoard.domain.Posts;

//등록
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

    //dto -> entity. db에 등록
    public Posts toEntity()
    {
        return Posts.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

}