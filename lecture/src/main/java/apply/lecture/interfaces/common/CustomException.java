package apply.lecture.interfaces.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    @Override
    public String getMessage(){
        return errorCode.getMessage();
    }

    public String getCode(){
        return errorCode.getCode();
    }

    public HttpStatus getHttpStatus(){
        return errorCode.getStatusCode();
    }
}
