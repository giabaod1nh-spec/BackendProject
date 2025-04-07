package com.example.trainning.point.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATOGERIZED_EXCEPTION(9999 , "Uncategorized error"),
    INVALID_KEY(1001, "Uncategorized error"),
    USER_NOT_FOUND(1002, "User not existed"),
    INVALID_PASSWORD(1004, "Password must be at least 6 characters"),
    UNAUTHETICATED(1006, "Unauthenticated")
    ;
    ErrorCode(int code , String message){
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;
}
