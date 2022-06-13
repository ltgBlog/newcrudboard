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
    private int hit;
    private Long userId;

    //entity -> dto. db를 조회
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.hit = entity.getHit();
        this.userId = entity.getUser().getId(); //왜래키를 통해서 Posts->User->id 필드 가져오기
    }
}