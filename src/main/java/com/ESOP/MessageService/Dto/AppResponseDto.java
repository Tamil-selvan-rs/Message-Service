package com.ESOP.MessageService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppResponseDto {
    private String statusCode;
    private String statusMessage;
    private String errorMessage;
    private Object data;
}
