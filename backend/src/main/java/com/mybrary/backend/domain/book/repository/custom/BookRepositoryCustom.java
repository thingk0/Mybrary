package com.mybrary.backend.domain.book.repository.custom;

import com.mybrary.backend.domain.book.dto.responseDto.BookForMainThreadDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.responseDto.MyBookGetDto;
import java.util.List;
import java.util.Optional;

public interface BookRepositoryCustom {


    Optional<Integer> countMyBook(Long bookShelfId);

    Optional<List<BookGetDto>> getAllBookByCategoryId(Long categoryId);

    Optional<List<MyBookGetDto>> getAllMyBookList(Long memeberId, Long categoryId);

    Optional<List<BookListGetFromPaperDto>> getBookListFromPaper(Long paperId);

    Optional<List<BookForMainThreadDto>> getBookForMainThread(Long writerId, Long paperId);
}
