package com.mybrary.backend.global.interceptor;

import java.security.Principal;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ChatStompInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("event=SEND-MESSAGE, message={}", message);
        Principal principal = accessor.getUser();
        if (principal != null) {
            log.info("event:USER-IDENTIFIED, email:{}", principal.getName());
        } else {
            log.warn("event:USER-NOT-IDENTIFIED, warning:Principal object is null");
        }

        return message;
    }

}

