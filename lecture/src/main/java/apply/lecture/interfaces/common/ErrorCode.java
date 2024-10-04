package apply.lecture.interfaces.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    LECTURE_EMPTY_INPUT("E001", HttpStatus.BAD_REQUEST,"신청 강의명이 없습니다."),
    USER_EMPTY_INPUT("E001", HttpStatus.BAD_REQUEST,"신청 유저아이디가 없습니다."),

    LECTURE_NOT_EXIST("E100", HttpStatus.BAD_REQUEST,"특강이 존재하지 않습니다."),
    LECTURE_ALREADY_APPLIED("E101", HttpStatus.BAD_REQUEST, "중복 신청입니다."),
    LECTURE_NOT_ENOUGH("E102", HttpStatus.BAD_REQUEST, "특강 수강 인원을 초과했습니다."),
    MEMBER_NOT_EXIST("E103", HttpStatus.BAD_REQUEST,"유저가 존재하지 않습니다.");
    private final String code;
    private final HttpStatus statusCode;
    private final String message;
}
