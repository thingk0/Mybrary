package com.mybrary.backend.domain.mybrary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mybrary")
public class MybraryController {

    @GetMapping
    public ResponseEntity<?> getMybrary() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{memberid}")
    public ResponseEntity<?> getOtherMybrary(
        @PathVariable(name = "memberid") long memberId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createMybrary() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateMybrary() {

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
