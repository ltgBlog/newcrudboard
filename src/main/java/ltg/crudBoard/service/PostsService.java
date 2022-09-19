package ltg.crudBoard.service;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.domain.Posts;
import ltg.crudBoard.domain.User;
import ltg.crudBoard.dto.PostsListResponseDto;
import ltg.crudBoard.dto.PostsResponseDto;
import ltg.crudBoard.dto.PostsSaveRequestDto;
import ltg.crudBoard.dto.PostsUpdateRequestDto;
import ltg.crudBoard.repository.PostsRepository;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService
{
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(String nickname, PostsSaveRequestDto requestDto)
    {
        User user = userRepository.findByNickname(nickname); //닉네임으로 유저를 찾고
        requestDto.setUser(user); //dto에 유저 저장 (Posts의 user_id(FK)에 저장하기 위해)

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //단, update는 직접 구현해야 한다..
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto)
    {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete (Long id)
    {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    //dto 반환
    public PostsResponseDto findById(Long id)
    {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));
        return new PostsResponseDto(entity);
    }


    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc()
    {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }//postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto로 변환 한 뒤 리스트로 반환.

    // 조회수 증가
    @Transactional
    public int updateHit(Long id)
    {
        return postsRepository.updateHit(id);
    }

    //페이징
    @Transactional
    public Page<Posts> paging(Pageable pageable)
    {
        return postsRepository.findAll(pageable);
    } //jpa에서는 페이징과 정렬은 findAll로 한다.

    //검색
    @Transactional
    public Page<Posts> search(String searchKeyword, String content, Pageable pageable)
    {
        Page<Posts> searchedList = postsRepository.findByTitleContainingOrContentContaining(searchKeyword, content, pageable);
        return searchedList;
    }



}