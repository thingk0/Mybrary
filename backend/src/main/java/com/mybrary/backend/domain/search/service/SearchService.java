package com.mybrary.backend.domain.search.service;

import com.mybrary.backend.domain.contents.thread.dto.ThreadSearchGetDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    List<ThreadSearchGetDto> searchThread(String keyword, Pageable page);

}
