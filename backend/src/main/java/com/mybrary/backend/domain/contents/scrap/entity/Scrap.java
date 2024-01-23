package com.mybrary.backend.domain.contents.scrap.entity;

import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.contents.paper.entity.Paper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Paper paper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Book book;

    private int paperSeq;

}


