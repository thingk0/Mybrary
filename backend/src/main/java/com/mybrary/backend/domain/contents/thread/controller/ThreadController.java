package com.mybrary.backend.domain.contents.thread.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/thread")
public class ThreadController {


    @GetMapping("/{id}")
    public ResponseEntity<?> getThread(
        @PathVariable(name = "id") Long threadId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createThread(@RequestBody String test) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateThread(@RequestBody String test,
                                          @PathVariable(name = "id") String paperId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteThread(@PathVariable(name = "id") Long threadId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<?> getThreadList(
        @RequestParam @PageableDefault(page = 0, size = 10) Pageable page) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/desk")
    public ResponseEntity<?> getMyThreadList(
        @RequestParam @PageableDefault(page = 0, size = 10) Pageable page) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/desk")
    public ResponseEntity<?> getOtherThreadList(@PathVariable(name = "id") Long mybraryId,
                                                @RequestParam @PageableDefault(page = 0, size = 10) Pageable page) {

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
