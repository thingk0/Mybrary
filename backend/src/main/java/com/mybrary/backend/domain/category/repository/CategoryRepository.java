package com.mybrary.backend.domain.category.repository;

import com.mybrary.backend.domain.category.dto.CategoryGetDto;
import com.mybrary.backend.domain.category.entity.Category;
import com.mybrary.backend.domain.category.repository.custom.CategoryRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

}
