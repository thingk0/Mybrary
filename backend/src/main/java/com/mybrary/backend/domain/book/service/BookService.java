package com.mybrary.backend.domain.book.service;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.book.dto.BookListGetDto;
import com.mybrary.backend.domain.book.dto.BookPostDto;
import com.mybrary.backend.domain.book.dto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.BookUpdateDto;
import java.util.List;

public interface BookService {

    BookListGetDto getBookList(Long memberId);

    Long createBook(BookPostDto bookPostDto);

    BookGetDto getBookInfo(Long bookId);

    Object updateBook(BookUpdateDto bookUpdateDto);

    Object deleteBook(Long bookId);

    Object subscribeBook(BookSubscribeDto bookSubscribeDto);

    Object unsubscribeBook(Long bookId);

    List<BookGetDto> getAllBookByCategoryId(Long categoryId);
}
