package com.mybrary.backend.global.handler;

import com.mybrary.backend.global.exception.email.FailedMessageTransmissionException;
import com.mybrary.backend.global.exception.email.InvalidAuthCodeException;
import com.mybrary.backend.global.exception.jwt.RefreshTokenNotFoundException;
import com.mybrary.backend.global.exception.member.DuplicateEmailException;
import com.mybrary.backend.global.exception.member.EmailNotFoundException;
import com.mybrary.backend.global.exception.member.InvalidLoginAttemptException;
import com.mybrary.backend.global.exception.member.PasswordMismatchException;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApiResponse response;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error("Exception = {}", e.getMessage());
        return response.error(ErrorCode.GLOBAL_UNEXPECTED_ERROR);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    protected ResponseEntity<?> handle(PasswordMismatchException e) {
        log.error("PasswordMismatchException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<?> handle(DuplicateEmailException e) {
        log.error("DuplicateEmailException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(FailedMessageTransmissionException.class)
    protected ResponseEntity<?> handle(FailedMessageTransmissionException e) {
        log.error("FailedMessageTransmissionException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(InvalidAuthCodeException.class)
    protected ResponseEntity<?> handle(InvalidAuthCodeException e) {
        log.error("InvalidAuthCodeException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(InvalidLoginAttemptException.class)
    protected ResponseEntity<?> handle(InvalidLoginAttemptException e) {
        log.error("InvalidLoginAttemptException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    protected ResponseEntity<?> handle(EmailNotFoundException e) {
        log.error("EmailNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    protected ResponseEntity<?> handle(RefreshTokenNotFoundException e) {
        log.error("RefreshTokenNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

}
