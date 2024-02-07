package com.mybrary.backend.domain.book.service;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.book.dto.BookListGetDto;
import com.mybrary.backend.domain.book.dto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.BookPostDto;
import com.mybrary.backend.domain.book.dto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.BookUpdateDto;
import java.util.List;

public interface BookService {

    BookListGetDto getBookList(Long memberId);

    Long createBook(BookPostDto bookPostDto);

    BookGetDto getBookInfo(Long bookId);

    Long updateBook(BookUpdateDto bookUpdateDto);

    Long deleteBook(Long bookId);

    Long subscribeBook(BookSubscribeDto bookSubscribeDto);

    Long unsubscribeBook(Long bookId);

    List<BookGetDto> getAllBookByCategoryId(Long categoryId);

    Long deletePaperFromBook(Long bookId, List<Long> paperIdList);

    List<BookListGetFromPaperDto> getBookListFromPaper(Long paperId);
}
