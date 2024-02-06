package com.mybrary.backend.domain.book.service.impl;

import com.mybrary.backend.domain.book.dto.BookGetDto;
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
    public List<BookGetDto> getAllBookByCategoryId(Long categoryId) {
        return bookRepository.getAllBookByCategoryId(categoryId);
    }
}
