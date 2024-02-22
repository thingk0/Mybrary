package com.mybrary.backend.domain.rollingpaper.controller;

import com.mybrary.backend.domain.rollingpaper.dto.RollingPaperDto;
import com.mybrary.backend.domain.rollingpaper.service.RollingPaperService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
public class RollingPaperSendController {

    private final SimpMessagingTemplate template;
    private final RollingPaperService rollingPaperService;


    @MessageMapping("/rollingPaper/{rollingPaperId}")
    public void sendRollingPaper(@DestinationVariable Long rollingPaperId,
                                 Principal principal,
                                 RollingPaperDto rollingPaper) {

        log.info("method=sendRollingPaper rollingPaperId={}, email={}",
                 rollingPaperId, principal.getName());

        RollingPaperDto updatedDto = rollingPaperService.update(principal.getName(), rollingPaper);
        template.convertAndSend("/sub/rollingPaper/" + rollingPaperId, updatedDto);
    }

}
