package com.mybrary.backend.domain.mybrary.repository;

import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import com.mybrary.backend.domain.mybrary.repository.custom.MybraryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MybraryRepository extends JpaRepository<Mybrary, Long>, MybraryRepositoryCustom {


}
