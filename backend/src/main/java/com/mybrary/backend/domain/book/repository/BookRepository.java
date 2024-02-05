package com.mybrary.backend.domain.book.repository;

import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.book.repository.custom.BookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
}
