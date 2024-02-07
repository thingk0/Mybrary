package com.mybrary.backend.domain.book.service.impl;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import com.mybrary.backend.domain.book.dto.BookListGetDto;
import com.mybrary.backend.domain.book.dto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.BookPostDto;
import com.mybrary.backend.domain.book.dto.BookSubscribeDto;
import com.mybrary.backend.domain.book.dto.BookUpdateDto;
import com.mybrary.backend.domain.book.repository.BookRepository;
import com.mybrary.backend.domain.book.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookListGetDto getBookList(Long memberId) {
        return null;
    }

    @Override
    public Long createBook(BookPostDto bookPostDto) {
        return null;
    }

    @Override
    public BookGetDto getBookInfo(Long bookId) {
        return null;
    }

    @Override
    public Long updateBook(BookUpdateDto bookUpdateDto) {
        return null;
    }

    @Override
    public Long deleteBook(Long bookId) {
        return null;
    }

    @Override
    public Long subscribeBook(BookSubscribeDto bookSubscribeDto) {
        return null;
    }

    @Override
    public Long unsubscribeBook(Long bookId) {
        return null;
    }

    @Override
    public List<BookGetDto> getAllBookByCategoryId(Long categoryId) {
        return bookRepository.getAllBookByCategoryId(categoryId);
    }

    @Override
    public Long deletePaperFromBook(Long bookId, List<Long> paperIdList) {
        return null;
    }

    @Override
    public List<BookListGetFromPaperDto> getBookListFromPaper(Long paperId) {
        return null;
    }

}
