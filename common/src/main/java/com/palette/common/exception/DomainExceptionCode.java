package com.palette.common.exception;

import org.springframework.http.HttpStatus;

public interface DomainExceptionCode {
    HttpStatus getStatus();
    String getMessage();
    String name();
}