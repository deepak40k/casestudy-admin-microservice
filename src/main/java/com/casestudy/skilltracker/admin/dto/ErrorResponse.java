package com.casestudy.skilltracker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private int status;
    private String message;
}
