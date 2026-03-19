package com.ESOP.MessageService.Service;

import com.ESOP.MessageService.Dto.AppResponseDto;
import com.ESOP.MessageService.Dto.MessageDto;

import java.util.List;
import java.util.Map;

public interface MessageService {
    AppResponseDto processSendSms(MessageDto messageDto);
    AppResponseDto ProcessCreateTemplate(List<Map<String, Object>> templateList);
}
