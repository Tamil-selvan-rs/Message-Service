package com.ESOP.MessageService.Controller;

import com.ESOP.MessageService.Dto.AppResponseDto;
import com.ESOP.MessageService.Dto.MessageDto;
import com.ESOP.MessageService.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/sendSMS")
    public AppResponseDto SendSms(@RequestBody MessageDto messageDto) {

        return messageService.processSendSms(messageDto);
    }
    @PostMapping("/createTemplate")
    public AppResponseDto CreateTemplate(@RequestBody List<Map<String, Object>> templateList) {
        return messageService.ProcessCreateTemplate(templateList);
    }
}
