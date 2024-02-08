package com.mybrary.backend.domain.book.service;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.book.dto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.BookPaperGetDto;
import com.mybrary.backend.domain.book.dto.BookPostDto;
import com.mybrary.backend.domain.book.dto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.BookUpdateDto;
import com.mybrary.backend.domain.category.dto.MyCategoryGetDto;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface BookService {

    List<MyCategoryGetDto> getMyBookList(Long memberId);

    Long createBook(String email, BookPostDto bookPostDto);

    BookPaperGetDto getBookInfo(String email, Long bookId);

    Long updateBook(String email, BookUpdateDto bookUpdateDto);

    void deleteBook(String email, Long bookId);

    Long subscribeBook(String email, BookSubscribeDto bookSubscribeDto);

    Long unsubscribeBook(String email, Long bookId);

    List<BookGetDto> getAllBookByCategoryId(Long categoryId);

    void deletePaperFromBook(String email, Long bookId, Long paperId);

    List<BookListGetFromPaperDto> getBookListFromPaper(Long paperId);

}
