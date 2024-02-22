package com.mybrary.backend.domain.rollingpaper.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
public class RollingPaperWebSocketController {

    @MessageMapping("/rp/{id}/draw")
    @SendTo("/rp/{id}")
    public String handleMessage(@DestinationVariable Long id,
                                @Payload String encodingString) {
        log.info("rolling-paper-id = {}", id);
        log.info("encodingString = {}", encodingString);
        return encodingString;
    }

}
