package com.example.trainning.point.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATOGERIZED_EXCEPTION(9999 , "Uncategorized error" , HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error" , HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002 , "User existed" , HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not existed" , HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    UNAUTHETICATED(1006, "Unauthenticated" , HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007 , "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be atleast {min} " , HttpStatus.BAD_REQUEST),
    OPERATION_NOT_ALLOWED(1009 , "User already a student" , HttpStatus.BAD_REQUEST),
    STUDENT_NOT_FOUND(1010, "Student not found", HttpStatus.NOT_FOUND),
    STUDENT_EXISTED(1011, "Student code already exists", HttpStatus.BAD_REQUEST),
    STUDENT_CODE_ALREADY_EXISTS(1012, "Student code already exists", HttpStatus.BAD_REQUEST)



    ;
    ErrorCode(int code , String message , HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
