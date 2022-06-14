package ltg.crudBoard.repository;

import ltg.crudBoard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>
{

}
