package ltg.crudBoard.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role
{

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    SOCIAL("ROLE_SOCIAL", "소셜 로그인 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");


    private final String key;
    private final String title;

}
