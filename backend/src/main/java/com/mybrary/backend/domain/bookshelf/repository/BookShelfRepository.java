package com.mybrary.backend.domain.bookshelf.repository;

import com.mybrary.backend.domain.bookshelf.entity.Bookshelf;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.custom.MybraryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookShelfRepository extends JpaRepository<Bookshelf, Long> {

}
