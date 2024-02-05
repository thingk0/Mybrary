package com.mybrary.backend.domain.contents.thread.entity;

import com.mybrary.backend.domain.base.BaseEntity;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "threads")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE threads SET is_deleted = TRUE WHERE threads_id = ?")
public class Threads extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threads_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mybrary_id")
    private Mybrary mybrary;

}

