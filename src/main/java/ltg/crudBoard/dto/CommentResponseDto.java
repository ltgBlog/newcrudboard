package ltg.crudBoard.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ltg.crudBoard.domain.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto
{
    private Long id;
    private String comment;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String nickname;
    private Long postsId;
    private Long userId;

    //entity -> dto (정보 조회)
    public CommentResponseDto(Comment comment)
    {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdDate=comment.getCreatedDate();
        this.modifiedDate=comment.getModifiedDate();
        this.nickname = comment.getUser().getNickname(); //댓글 테이블에서, 연관관계로 유저 찾아내고, 그 유저의 닉네임을 가져옴
        this.postsId = comment.getPosts().getId(); //마찬가지이다.
        this.userId = comment.getUser().getId(); //로그인 유저의 id와 댓글을 쓴 유저의 id를 비교하기 위해 추가


    }

}
