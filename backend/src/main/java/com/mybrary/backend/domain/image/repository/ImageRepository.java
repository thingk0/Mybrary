package com.mybrary.backend.domain.image.repository;

import com.mybrary.backend.domain.image.entity.Image;
import com.mybrary.backend.domain.image.repository.custom.ImageRepositoryCustom;
import com.mybrary.backend.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {

}
