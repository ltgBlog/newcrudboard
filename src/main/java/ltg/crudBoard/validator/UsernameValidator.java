package ltg.crudBoard.validator;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class UsernameValidator extends AbstractValidator<UserSaveRequestDto>
{
    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserSaveRequestDto dto, Errors errors)
    {
        if(userRepository.existsByUsername(dto.toEntity().getUsername()))
        {
            errors.rejectValue("username", "아이디 중복 오류", "이미 사용중인 아이디입니다.");
        }
    }
}
