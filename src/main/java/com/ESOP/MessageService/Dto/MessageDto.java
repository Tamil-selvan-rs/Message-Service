package com.ESOP.MessageService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String uniqueValue;
    private BigInteger templateId;
    private String channelType;
}
