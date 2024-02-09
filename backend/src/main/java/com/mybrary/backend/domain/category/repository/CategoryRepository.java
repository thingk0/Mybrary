package com.mybrary.backend.domain.category.repository;

import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.category.repository.custom.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

}
