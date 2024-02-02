package com.mybrary.backend.domain.member.service;

public interface MailService {

    String sendEmailVerification(String email);

    boolean confirmAuthCode(String email, String authNum);
}
