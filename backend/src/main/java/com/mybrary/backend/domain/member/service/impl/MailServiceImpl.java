package com.mybrary.backend.domain.member.service.impl;

import com.mybrary.backend.domain.member.service.MailService;
import com.mybrary.backend.global.exception.email.FailedMessageTransmissionException;
import com.mybrary.backend.global.format.ErrorCode;
import com.mybrary.backend.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Value("${email.from}")
    private String sender;

    @Value("${email.subject}")
    private String subject;


    @Value("${email.authCode.duration}")
    private long emailAuthCodeDuration;

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    /**
     * 사용자에게 이메일 인증을 보냅니다.
     *
     * @param receiver : 수신자의 이메일 주소
     * @return : 사용자에게 보낸 인증 코드
     */
    @Override
    public String sendEmailVerification(String receiver) {
        int authCode = generateAuthCode();
        String content = createMailContent(authCode);
        dispatchVerificationCode(receiver, content);
        redisUtil.setDataExpire(Integer.toString(authCode), receiver, emailAuthCodeDuration);
        return Integer.toString(authCode);
    }

    /**
     * 제공된 인증 코드가 일치하는지 확인합니다.
     *
     * @param email   : 인증을 확인할 이메일 주소
     * @param authNum : 사용자가 입력한 인증번호
     * @return : 인증 번호가 일치하면 true, 그렇지 않으면 false 를 반환
     */
    @Override
    public boolean confirmAuthCode(String email, String authNum) {
        return email.equals(redisUtil.getData(authNum));
    }


    /**
     * 인증 코드를 생성합니다.
     *
     * @return : 6자리 인증코드
     */
    private int generateAuthCode() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

    /**
     * 인증 코드를 담은 이메일을 발송합니다.
     *
     * @param receiver : 수신자의 이메일 주소
     * @param content  : 이메일 내용
     */
    private void dispatchVerificationCode(String receiver, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new FailedMessageTransmissionException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    /**
     * 이메일 내용을 생성합니다.
     *
     * @param authCode : 인증번호
     * @return : 생성된 이메일 내용
     */
    private String createMailContent(int authCode) {
        return "<html>" +
            "<body>" +
            "<div style='font-family: Arial, sans-serif; text-align: center; color: #333;'>" +
            "<h2>MyBrary 에 오신 것을 환영합니다!</h2>" +
            "<p>회원가입해 주셔서 감사합니다. 여러분의 참여를 진심으로 환영합니다.</p>" +
            "<p>인증 번호는 다음과 같습니다:</p>" +
            "<div style='font-size: 18px; margin: 20px; padding: 10px; background-color: #f2f2f2; border: 1px solid #e0e0e0; display: inline-block;'>"
            +
            authCode +
            "</div>" +
            "<p>회원가입을 완료하기 위해 이 인증번호를 가입 페이지에 입력해 주세요.</p>" +
            "<br>" +
            "<p>본 이메일을 요청하지 않으셨다면, 무시해 주세요.</p>" +
            "</div>" +
            "</body>" +
            "</html>";
    }

}

