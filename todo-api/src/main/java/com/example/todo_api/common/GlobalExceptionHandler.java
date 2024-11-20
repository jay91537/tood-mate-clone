package com.example.todo_api.common;

import com.example.todo_api.common.response.ErrorResponse;
import com.example.todo_api.common.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// 400을 쏠지, 500을 쏠지 여기서 정하고, 에러 메세지는 원래 지정해뒀던 에러메세지가 나감
public class GlobalExceptionHandler {

    // 특정 핸들러로 처리하지 못한 에러를 처리
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse((ex.getMessage()));

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    // 직접 badRequestException 호출
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse((ex.getMessage()));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // dto에서 유효성 검사가 실패했을때 대신 응답하는 핸들러
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // HTTP 바디의 Json 파일의 형식이 잘못된 형식일때 대신 응답하는 핸들러
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "malFormed JSON";
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(errorResponse);
    }


}
