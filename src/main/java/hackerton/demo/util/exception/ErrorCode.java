package hackerton.demo.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 서버 에러 (S)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "내부 서버 에러가 발생했습니다."),
    API_UNKNOWN_FINISH_REASON(HttpStatus.INTERNAL_SERVER_ERROR, "API_UNKNOWN_FINISH_REASON", "알 수 없는 이유로 응답을 불러올 수 없습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DATABASE_ERROR", "데이터베이스 오류가 발생했습니다."),

    // Firebase 계정 삭제 실패
    FIREBASE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FIREBASE_DELETE_FAIL", "[AUTH] Firebase 계정 삭제에 실패했습니다."),

    //멤버 조회 관련
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "[MEMBER] 해당 사용자를 찾을 수 없습니다."),


    // 회원 인증 관련 (SecurityContext 기반)
    FIREBASE_CONTEXT_MISSING(HttpStatus.UNAUTHORIZED, "FIREBASE_CONTEXT_MISSING", "[AUTH] SecurityContext에 인증 정보가 없습니다."),
    FIREBASE_TOKEN_INVALID_TYPE(HttpStatus.UNAUTHORIZED, "FIREBASE_TOKEN_INVALID_TYPE", "[AUTH] 인증 주체가 FirebaseToken이 아닙니다."),
    FIREBASE_TOKEN_MISSING_UID(HttpStatus.UNAUTHORIZED, "FIREBASE_TOKEN_MISSING_UID", "[AUTH] Firebase 토큰에서 UID를 추출할 수 없습니다."),

    // Firebase 필터 레벨 인증 에러 (요청 헤더 및 검증)
    FIREBASE_AUTH_HEADER_MISSING(HttpStatus.UNAUTHORIZED, "FIREBASE_AUTH_HEADER_MISSING", "[AUTH] Authorization 헤더가 없습니다."),
    FIREBASE_AUTH_INVALID_FORMAT(HttpStatus.UNAUTHORIZED, "FIREBASE_AUTH_INVALID_FORMAT", "[AUTH] Bearer 형식이 아닙니다."),
    FIREBASE_AUTH_FAILURE(HttpStatus.UNAUTHORIZED, "FIREBASE_AUTH_FAILURE", "[AUTH] Firebase 인증에 실패했습니다.");



    private final HttpStatus status;
    private final String error;
    private final String message;

    ErrorCode(final HttpStatus status, final String error, final String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
