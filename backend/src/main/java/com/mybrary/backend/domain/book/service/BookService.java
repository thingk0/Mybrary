package com.mybrary.backend.domain.book.service;

import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookPaperGetDto;
import com.mybrary.backend.domain.book.dto.requestDto.BookPostDto;
import com.mybrary.backend.domain.book.dto.requestDto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.requestDto.BookUpdateDto;
import com.mybrary.backend.domain.category.dto.responseDto.MyCategoryGetDto;
import java.util.List;

public interface BookService {

    List<MyCategoryGetDto> getMyBookList(Long memberId);

    Long createBook(String email, BookPostDto bookPostDto);

    BookPaperGetDto getBookInfo(String email, Long bookId);

    Long updateBook(String email, BookUpdateDto bookUpdateDto);

    void deleteBook(String email, Long bookId);

    Long subscribeBook(String email, BookSubscribeDto bookSubscribeDto);

    Long unsubscribeBook(String email, Long bookId);

    List<BookGetDto> getAllBookByCategoryId(String email, Long categoryId);

    void deletePaperFromBook(String email, Long bookId, Long paperId);

    List<BookListGetFromPaperDto> getBookListFromPaper(Long memberId, Long paperId);

}
