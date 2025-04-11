package com.example.trainning.point.configuration;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
    //Ctr I de implement method trong interface
        ErrorCode errorCode = ErrorCode.UNAUTHETICATED;

        response.setStatus(errorCode.getStatusCode().value());
        //Tra ve voi body la JSON , setContentType
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();


        //Viet 1 objMapper de covert ve JSON
        ObjectMapper objectMapper = new ObjectMapper();
        //Viet response tra ve de commit cho user
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
        //Sau khi xong config authenticationEntryPoint
     }
}

