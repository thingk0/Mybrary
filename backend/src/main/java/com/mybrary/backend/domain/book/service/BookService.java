package com.mybrary.backend.domain.book.service;

import com.mybrary.backend.domain.book.dto.BookGetDto;
import java.util.List;

public interface BookService {

    List<BookGetDto> getAllBookByCategoryId(Long categoryId);
}
