package com.mybrary.backend.domain.book.repository.custom;

import com.mybrary.backend.domain.book.dto.responseDto.BookForMainThreadDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.book.dto.responseDto.BookListGetFromPaperDto;
import com.mybrary.backend.domain.book.dto.responseDto.MyBookGetDto;
import com.mybrary.backend.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {


    Optional<Integer> countMyBook(Long bookShelfId);

    Optional<List<BookGetDto>> getAllBookByCategoryId(Long categoryId);

    Optional<List<MyBookGetDto>> getAllMyBookList(Long memeberId, Long categoryId);

    Optional<List<BookListGetFromPaperDto>> getBookListFromPaper(Long paperId);

    Optional<List<BookForMainThreadDto>> getBookForMainThread(Long writerId, Long paperId);

    Optional<List<BookGetDto>> searchBookByKeyword(Long myId, String keyword, Pageable page);

    Optional<Member> findMember(Long bookId);

    Optional<Member> findMemberByCategoryId(Long categoryId);
}
