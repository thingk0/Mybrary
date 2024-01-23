package com.mybrary.backend.domain.bookmarker.entity;

import com.mybrary.backend.domain.book.entity.Book;
import com.mybrary.backend.domain.mybrary.entity.Mybrary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bookmarker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Mybrary mybrary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Book book;

    private String colorCode;

    private int bookmarkerIndex;


}



