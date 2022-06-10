package ltg.crudBoard.repository;

import ltg.crudBoard.domain.Posts;
import ltg.crudBoard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    //유저 아이디로 데이터를 가져옴.  optional??
    Optional<User> findByUsername(String username);

    //By 뒤 내용으로 존재하는지 알아낸다.
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

}
