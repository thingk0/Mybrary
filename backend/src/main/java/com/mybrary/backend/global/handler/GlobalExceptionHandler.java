package com.mybrary.backend.global.handler;

import com.mybrary.backend.global.exception.RollingPaperNotFoundException;
import com.mybrary.backend.global.exception.book.BookAccessDeniedException;
import com.mybrary.backend.global.exception.book.BookAlreadySubscribeException;
import com.mybrary.backend.global.exception.book.BookCreateException;
import com.mybrary.backend.global.exception.book.BookDeleteException;
import com.mybrary.backend.global.exception.book.BookNotFoundException;
import com.mybrary.backend.global.exception.book.BookSubscribeException;
import com.mybrary.backend.global.exception.book.BookUpdateException;
import com.mybrary.backend.global.exception.bookshelf.BookshelfNotFoundException;
import com.mybrary.backend.global.exception.category.CategoryAccessDeniedException;
import com.mybrary.backend.global.exception.category.CategoryOwnerNotFoundException;
import com.mybrary.backend.global.exception.chat.ChatJoinMemberNotFoundException;
import com.mybrary.backend.global.exception.chat.ChatRoomNotFoundException;
import com.mybrary.backend.global.exception.chat.InvalidChatRoomAccessException;
import com.mybrary.backend.global.exception.email.FailedMessageTransmissionException;
import com.mybrary.backend.global.exception.email.InvalidAuthCodeException;
import com.mybrary.backend.global.exception.image.ImageNotFoundException;
import com.mybrary.backend.global.exception.jwt.AccessTokenNotFoundException;
import com.mybrary.backend.global.exception.jwt.RefreshTokenNotFoundException;
import com.mybrary.backend.global.exception.member.DuplicateEmailException;
import com.mybrary.backend.global.exception.member.EmailNotFoundException;
import com.mybrary.backend.global.exception.member.FollowNotFoundException;
import com.mybrary.backend.global.exception.member.FollowerNotFoundException;
import com.mybrary.backend.global.exception.member.FollowingNotFoundException;
import com.mybrary.backend.global.exception.member.InvalidLoginAttemptException;
import com.mybrary.backend.global.exception.member.InvalidNicknameException;
import com.mybrary.backend.global.exception.member.MissingPathVariableException;
import com.mybrary.backend.global.exception.member.PasswordMismatchException;
import com.mybrary.backend.global.exception.member.ProfileUpdateException;
import com.mybrary.backend.global.exception.mybrary.MybraryAccessDeniedException;
import com.mybrary.backend.global.exception.mybrary.MybraryNotFoundException;
import com.mybrary.backend.global.exception.mybrary.NotMybraryException;
import com.mybrary.backend.global.exception.paper.PaperAccessDeniedException;
import com.mybrary.backend.global.exception.paper.PaperDeleteException;
import com.mybrary.backend.global.exception.paper.PaperListNotFoundException;
import com.mybrary.backend.global.exception.pickbook.PickBookNotFoundException;
import com.mybrary.backend.global.exception.scrap.ScrapNotFoundException;
import com.mybrary.backend.global.exception.tag.TagNotFoundException;
import com.mybrary.backend.global.exception.thread.ThreadAccessDeniedException;
import com.mybrary.backend.global.exception.thread.ThreadIdNotFoundException;
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
        e.printStackTrace();
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

    @ExceptionHandler(InvalidNicknameException.class)
    protected ResponseEntity<?> handle(InvalidNicknameException e) {
        log.error("InvalidNicknameException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    protected ResponseEntity<?> handle(MissingPathVariableException e) {
        log.error("MissingPathVariableException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(MybraryNotFoundException.class)
    protected ResponseEntity<?> handle(MybraryNotFoundException e) {
        log.error("MybraryNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(NotMybraryException.class)
    protected ResponseEntity<?> handle(NotMybraryException e) {
        log.error("NotMybraryException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ImageNotFoundException.class)
    protected ResponseEntity<?> handle(ImageNotFoundException e) {
        log.error("ImageNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ProfileUpdateException.class)
    protected ResponseEntity<?> handle(ProfileUpdateException e) {
        log.error("ProfileUpdateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(CategoryOwnerNotFoundException.class)
    protected ResponseEntity<?> handle(CategoryOwnerNotFoundException e) {
        log.error("CategoryOwnerNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookCreateException.class)
    protected ResponseEntity<?> handle(BookCreateException e) {
        log.error("BookCreateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookUpdateException.class)
    protected ResponseEntity<?> handle(BookUpdateException e) {
        log.error("BookUpdateException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(PaperListNotFoundException.class)
    protected ResponseEntity<?> handle(PaperListNotFoundException e) {
        log.error("PaperListNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(TagNotFoundException.class)
    protected ResponseEntity<?> handle(TagNotFoundException e) {
        log.error("TagNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ThreadIdNotFoundException.class)
    protected ResponseEntity<?> handle(ThreadIdNotFoundException e) {
        log.error("ThreadIdNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<?> handle(BookNotFoundException e) {
        log.error("BookNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(PickBookNotFoundException.class)
    protected ResponseEntity<?> handle(PickBookNotFoundException e) {
        log.error("PickBookNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookDeleteException.class)
    protected ResponseEntity<?> handle(BookDeleteException e) {
        log.error("BookDeleteException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookSubscribeException.class)
    protected ResponseEntity<?> handle(BookSubscribeException e) {
        log.error("BookSubscribeException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookAlreadySubscribeException.class)
    protected ResponseEntity<?> handle(BookAlreadySubscribeException e) {
        log.error("BookAlreadySubscribeException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(PaperDeleteException.class)
    protected ResponseEntity<?> handle(PaperDeleteException e) {
        log.error("PaperDeleteException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ScrapNotFoundException.class)
    protected ResponseEntity<?> handle(ScrapNotFoundException e) {
        log.error("ScrapNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookshelfNotFoundException.class)
    protected ResponseEntity<?> handle(BookshelfNotFoundException e) {
        log.error("BookshelfNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ChatRoomNotFoundException.class)
    protected ResponseEntity<?> handle(ChatRoomNotFoundException e) {
        log.error("ChatRoomNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ChatJoinMemberNotFoundException.class)
    protected ResponseEntity<?> handle(ChatJoinMemberNotFoundException e) {
        log.error("ChatJoinMemberNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(FollowingNotFoundException.class)
    protected ResponseEntity<?> handle(FollowingNotFoundException e) {
        log.error("FollowingNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(FollowerNotFoundException.class)
    protected ResponseEntity<?> handle(FollowerNotFoundException e) {
        log.error("FollowerNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(FollowNotFoundException.class)
    protected ResponseEntity<?> handle(FollowNotFoundException e) {
        log.error("FollowNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(ThreadAccessDeniedException.class)
    protected ResponseEntity<?> handle(ThreadAccessDeniedException e) {
        log.error("ThreadAccessDeniedException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(PaperAccessDeniedException.class)
    protected ResponseEntity<?> handle(PaperAccessDeniedException e) {
        log.error("PaperAccessDeniedException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(MybraryAccessDeniedException.class)
    protected ResponseEntity<?> handle(MybraryAccessDeniedException e) {
        log.error("MybraryAccessDeniedException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(CategoryAccessDeniedException.class)
    protected ResponseEntity<?> handle(CategoryAccessDeniedException e) {
        log.error("CategoryAccessDeniedException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(BookAccessDeniedException.class)
    protected ResponseEntity<?> handle(BookAccessDeniedException e) {
        log.error("BookAccessDeniedException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(InvalidChatRoomAccessException.class)
    protected ResponseEntity<?> handle(InvalidChatRoomAccessException e) {
        log.error("InvalidChatRoomAccessException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(RollingPaperNotFoundException.class)
    protected ResponseEntity<?> handle(RollingPaperNotFoundException e) {
        log.error("RollingPaperNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

    @ExceptionHandler(AccessTokenNotFoundException.class)
    protected ResponseEntity<?> handle(AccessTokenNotFoundException e) {
        log.error("AccessTokenNotFoundException = {}", e.getErrorCode().getMessage());
        return response.error(e.getErrorCode());
    }

}
