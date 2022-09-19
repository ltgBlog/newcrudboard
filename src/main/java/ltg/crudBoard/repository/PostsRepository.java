package ltg.crudBoard.repository;

import ltg.crudBoard.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long>
{
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    //조회수 쿼리
    @Modifying//조회를 제외한, 데이터 변경 시 사용됨.
    @Query("update Posts p set p.hit = p.hit + 1 where p.id = :id")
    int updateHit(@Param("id") Long id);

    //검색,정렬. 제목으로 찾고, Containing이 있으므로 검색하는 단어가 포함된 모든 제목을 찾는다.
    Page<Posts> findByTitleContainingOrContentContaining(String searchKeyword, String content, Pageable pageable);
}
