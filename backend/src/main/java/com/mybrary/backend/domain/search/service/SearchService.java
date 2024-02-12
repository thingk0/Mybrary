package com.mybrary.backend.domain.search.service;

import com.mybrary.backend.domain.book.dto.responseDto.BookGetDto;
import com.mybrary.backend.domain.contents.thread.dto.responseDto.ThreadSearchGetDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    List<String> listSuggestedTerms(String keyword);

    List<ThreadSearchGetDto> searchThread(String keyword, Pageable page);

    List<BookGetDto> searchBook(String email, String keyword, Pageable page);
}
