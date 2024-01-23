package com.mybrary.backend.domain.contents.thread.controller;


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

    @GetMapping("/{threadid}")
    public ResponseEntity<?> getThread(
        @PathVariable(name = "threadid") long threadId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createThread(@RequestBody String test) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateThread(@RequestBody String test,
                                          @PathVariable(name = "paperid") String paperId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{threadid}")
    public ResponseEntity<?> deleteThread(
        @PathVariable(name = "threadid") long threadId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<?> getThreadList(
        @RequestParam(name = "page") int pageNum) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/desk")
    public ResponseEntity<?> getMyThreadList(
        @RequestParam(name = "page") int pageNum) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/desk")
    public ResponseEntity<?> getOtherThreadList(
        @RequestParam(name = "page") int pageNum,
        @RequestParam(name = "mybraryid") Long mybraryId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
