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

    @MessageMapping("/rollingpaper/{rollingpaperId}")
    @SendTo("/sub/rollingpaper/{rollingpaperId}")
    public String handleMessage(@DestinationVariable String rollingPaperId,
                                @Payload String encodingString) {
        log.info("paperId = {}", rollingPaperId);
        log.info("encodingString = {}", encodingString);
        return encodingString;
    }

}
