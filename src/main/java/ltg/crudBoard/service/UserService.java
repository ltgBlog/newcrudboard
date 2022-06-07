package ltg.crudBoard.service;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(UserSaveRequestDto dto)
    {
        dto.setPassword(encoder.encode(dto.getPassword()));

        return userRepository.save(dto.toEntity()).getId();
    }
    //비밀번호를 해쉬 암호화 후 save
}
