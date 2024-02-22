package com.mybrary.backend.domain.contents.thread.repository;

import com.mybrary.backend.domain.contents.thread.entity.Thread;
import com.mybrary.backend.domain.contents.thread.repository.custom.ThreadRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long>, ThreadRepositoryCustom {
}
