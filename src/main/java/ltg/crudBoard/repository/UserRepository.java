package ltg.crudBoard.repository;

import ltg.crudBoard.domain.Posts;
import ltg.crudBoard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{

}
