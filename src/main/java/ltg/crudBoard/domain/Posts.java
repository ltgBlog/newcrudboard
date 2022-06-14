package ltg.crudBoard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Posts extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;


    private String writer;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int hit;

    //User 테이블의 id필드를 외래키로 갖는다
    @ManyToOne(fetch = FetchType.LAZY) //다대일 관계 매핑 정보. Posts와 User는 N:1관계
    @JoinColumn(name = "user_id") //외래키 매핑
    private User user;

    @OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    //게시글 수정
    public void update(String title, String content)
    {
        this.title = title;
        this.content = content;
    }
}
