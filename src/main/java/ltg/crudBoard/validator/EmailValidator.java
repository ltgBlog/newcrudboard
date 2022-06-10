package ltg.crudBoard.validator;

import lombok.RequiredArgsConstructor;
import ltg.crudBoard.dto.UserSaveRequestDto;
import ltg.crudBoard.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class EmailValidator extends AbstractValidator<UserSaveRequestDto>
{
    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserSaveRequestDto dto, Errors errors)
    {
        if(userRepository.existsByEmail(dto.toEntity().getEmail()))
        {
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일입니다.");
        }
    }
}
