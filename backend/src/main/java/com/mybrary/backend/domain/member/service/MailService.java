package com.mybrary.backend.domain.member.service;

import jakarta.servlet.http.HttpServletResponse;

public interface MailService {

    String sendEmailVerification(String email);

    boolean confirmAuthCode(String email, String authNum, HttpServletResponse servletResponse);
}
