package ltg.crudBoard.dto;

import lombok.*;
import ltg.crudBoard.domain.Posts;
import ltg.crudBoard.domain.User;

//등록
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsSaveRequestDto
{
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int hit;
    private User user;
    private String filename;
    private String filepath;

    //dto -> entity. db에 등록
    public Posts toEntity()
    {
        return Posts.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(writer)
                .hit(0)
                .user(user)
                .filename(filename)
                .filepath(filepath)
                .build();
    }

}