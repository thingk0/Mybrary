package com.mybrary.backend.domain.member.controller;

import com.mybrary.backend.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Member 컨트롤러", description="Member Controller API")
@RestController
public class MemberController {

  @Operation(summary = "일반회원가입", description = "일반 회원가입")
  @PostMapping("/member")
  public ResponseEntity<?> singup(@RequestBody Member member){
    return new ResponseEntity<>(HttpStatus.OK);
  }






}
