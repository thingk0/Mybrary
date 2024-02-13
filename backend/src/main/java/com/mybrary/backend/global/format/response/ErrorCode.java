package com.mybrary.backend.global.format.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 글로벌 예외 처리
    GLOBAL_UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 예기치 않은 서버 오류가 발생했습니다."),

    // 요청 관련 예외 처리
    REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 미디어 타입입니다."),
    MISSING_SERVLET_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    SERVLET_REQUEST_BINDING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 바인딩 오류가 발생했습니다."),
    CONVERSION_NOT_SUPPORTED(HttpStatus.INTERNAL_SERVER_ERROR, "지원되지 않는 변환 유형입니다."),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "타입 불일치 오류가 발생했습니다."),
    MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "메시지를 읽을 수 없습니다."),
    MESSAGE_NOT_WRITABLE(HttpStatus.INTERNAL_SERVER_ERROR, "메시지를 쓸 수 없습니다."),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "메서드 인자가 유효하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    MISSING_PATH_VARIABLE(HttpStatus.BAD_REQUEST, "PathVariable 파라미터가 요청에 포함되지 않았습니다."),

    ACCESS_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "액세스 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "새로운 로그인이 필요합니다. 재로그인을 진행해주세요."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "로그인 정보 형식이 올바르지 않습니다."),
    MODIFIED_TOKEN_DETECTED(HttpStatus.UNAUTHORIZED, "로그인 정보가 변경되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "토큰 형식이 유효하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "서비스 이용을 위해 로그인이 필요합니다."),

    // 회원 관련 예외 처리
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "회원 가입에 실패했습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 혹은 패스워드 정보가 정확하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "유효하지 않은 닉네임입니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "인증 이메일 전송에 실패했습니다."),
    AUTH_CODE_INVALID(HttpStatus.BAD_REQUEST, "인증 코드가 유효하지 않습니다."),

    PROFILE_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "회원정보를 수정할 수 없습니다."),

    // 팔로우 관련 예외 처리
    FOLLOWING_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로잉 리스트를 찾을 수 없습니다."),
    FOLLOWER_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로워 리스트를 찾을 수 없습니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 데이터를 찾을 수 없습니다."),

    // 마이브러리 예외 처리
    MYBRARY_NOT_FOUND(HttpStatus.NOT_FOUND, "마이브러리를 찾을 수 없습니다."),
    MYBRARY_MISMATCH(HttpStatus.BAD_REQUEST, "마이브러리 수정을 할 수 없습니다."),
    MYBRARY_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "마이브러리에 접근할 수 없습니다."),

    // 책 예외 처리
    BOOK_CREATE_FAILED(HttpStatus.BAD_REQUEST, "책을 생성할 수 없습니다."),
    BOOK_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "책을 수정할 수 없습니다."),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "책을 찾을 수 없습니다."),
    BOOK_DELETE_FAILED(HttpStatus.BAD_REQUEST, "책을 삭제할 수 없습니다."),
    BOOK_SUBSCRIBE_FAILED(HttpStatus.BAD_REQUEST, "책을 구독할 수 없습니다."),
    BOOK_ALREADY_SUBSCRIBE(HttpStatus.BAD_REQUEST, "책이 현재 카테고리에 이미 꽂혀있습니다."),
    PAPER_DELETE_FAILED(HttpStatus.BAD_REQUEST, "본인이 만든 책이 아닙니다."),
    BOOK_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "책에 접근할 수 없습니다."),

    // 책장 예외 처리
    BOOK_SHELF_NOT_FOUND(HttpStatus.NOT_FOUND, "책장을 찾을 수 없습니다."),

    // 픽북 예외 처리
    PICKBOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "픽북을 찾을 수 없습니다."),

    // 카테고리 예외 처리
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    CATEGORY_OWNER_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리의 주인을 찾을 수 없습니다."),
    CATEGORY_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "카테고리에 접근할 수 없습니다."),

    // 스레드 예외 처리
    THREADID_NOT_FOUND(HttpStatus.NOT_FOUND, "스레드 아이디를 찾을 수 없습니다."),
    MAIN_THREAD_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "메인피드에서 스레드 리스트 찾을 수 없습니다."),
    THREAD_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "스레드에 접근할 수 없습니다."),

    // 페이퍼 예외 처리
    PAPERLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "페이퍼리스트를 찾을 수 없습니다."),
    PAPER_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "페이퍼에 접근할 수 없습니다."),

    // 스크랩 예외 처리
    SCRAP_NOT_FOUND(HttpStatus.NOT_FOUND, "스크랩을 찾을 수 없습니다."),

    // 태그 예외 처리
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "태그리스트를 찾을 수 없습니다."),

    // 좋아요 예외 처리
    // 이미지 예외 처리
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다."),

    // 채팅 예외 처리
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    CHAT_JOIN_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "상대방을 찾을 수 없습니다."),
    INVALID_CHATROOM_ACCESS(HttpStatus.UNAUTHORIZED, "해당 사용자는 지정된 채팅방의 참여자가 아닙니다."),
    INVALID_MESSAGE(HttpStatus.BAD_REQUEST, "잘못된 메시지 형식입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 검증 실패하였습니다."),

    ROLLING_PAPER_NOT_FOUND (HttpStatus.NOT_FOUND, "롤링페이퍼를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}


