package ltg.crudBoard.dto;


import lombok.*;
import ltg.crudBoard.domain.Comment;
import ltg.crudBoard.domain.Posts;
import ltg.crudBoard.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto
{
    private Long id;
    private String comment;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private User user;
    private Posts posts;

    //dto -> entity (db에 저장)
    public Comment toEntity()
    {
        return Comment.builder()
                .id(id)
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .user(user)
                .posts(posts)
                .build();
    }

}
