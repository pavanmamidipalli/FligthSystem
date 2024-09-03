package com.passenger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private Object results;
    private Integer noOfRecords;
    private Object errorMessage;
    private String errorType;
    private String exceptionMessage;
}
