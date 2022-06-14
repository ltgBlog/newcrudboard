package ltg.crudBoard.service;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.domain.Comment;
import ltg.crudBoard.domain.Posts;
import ltg.crudBoard.domain.User;
import ltg.crudBoard.dto.CommentRequestDto;
import ltg.crudBoard.repository.CommentRepository;
import ltg.crudBoard.repository.PostsRepository;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentsService
{
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    //create
    @Transactional
    public Long commentSave(String nickname, Long id, CommentRequestDto dto)
    {
        User user = userRepository.findByNickname(nickname);
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글을 작성할 수 없습니다"));

        dto.setUser(user);
        dto.setPosts(posts);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getId();
    }

}
