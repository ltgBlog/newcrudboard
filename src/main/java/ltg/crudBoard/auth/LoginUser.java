package ltg.crudBoard.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //이 어노테이션이 생설될 수 있는 위치 지정
@Retention(RetentionPolicy.RUNTIME) //어느 시점까지 어노테이션의 메모리를 가져갈 지 설정한다.(런타임 종료까지 메모리 살아있음)
public @interface LoginUser //이 파일을 어노테이션 클래스로 지정. (LoginUser 어노테이션 생성)
{

}
