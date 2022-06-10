package ltg.crudBoard.service;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(UserSaveRequestDto dto)
    {
        //비밀번호를 해쉬 암호화 후 save
        dto.setPassword(encoder.encode(dto.getPassword()));

        return userRepository.save(dto.toEntity()).getId();
    }

    //회원가입 시 유효성 체크
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(BindingResult bindingResult)
    {
        Map<String, String> validatorResult = new HashMap<>();

        // 에러난 필드 목록을 받음
        for(FieldError error : bindingResult.getFieldErrors())
        {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }



}
