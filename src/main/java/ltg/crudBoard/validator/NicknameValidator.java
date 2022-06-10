package ltg.crudBoard.validator;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class NicknameValidator extends AbstractValidator<UserSaveRequestDto>
{
    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserSaveRequestDto dto, Errors errors)
    {
        if(userRepository.existsByNickname(dto.toEntity().getNickname()))
        {
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임입니다.");
        }
    }
}
