package com.kaban.kabanplatform.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Instant timestamp;

    private int status;
    private String error;
    private String message;
    private String errorCode;
    private String category;
    private String path;
    private String method;
    private String context;
    private Map<String, String> fieldErrors;
    private String traceId;
}
