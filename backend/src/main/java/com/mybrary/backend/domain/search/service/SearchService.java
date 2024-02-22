package com.mybrary.backend.domain.search.service;

import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.elastic.indices.PaperDocument;
import com.mybrary.backend.domain.member.dto.responseDto.MemberGetDto;
import com.mybrary.backend.domain.search.dto.SearchPaperResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    List<PaperDocument> getPaperDocumentListByThreadId(Long threadId);

    Page<SearchPaperResponseDto> searchThread(String email, String keyword, Pageable page);

    List<BookGetDto> searchBook(String email, String keyword, Pageable page);

    List<MemberGetDto> searchAccount(String email, String keyword, Pageable page);

    List<String> getRecentSearchTerms();

    List<String> getRealTimeSuggestedSearchTerms(String keyword);
}
