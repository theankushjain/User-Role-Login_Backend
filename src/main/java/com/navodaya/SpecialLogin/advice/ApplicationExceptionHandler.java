package com.navodaya.SpecialLogin.advice;

//import com.navodaya.SpecialLogin.exception.MenuNotFoundException;
import com.navodaya.SpecialLogin.exception.MenuNotFoundException;
import com.navodaya.SpecialLogin.exception.RoleNotFoundException;
import com.navodaya.SpecialLogin.exception.TokenNotFoundException;
import com.navodaya.SpecialLogin.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String,String> errorMap=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String,String> handleBusinessException(UserNotFoundException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TokenNotFoundException.class)
    public Map<String,String> handleBusinessException(TokenNotFoundException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MenuNotFoundException.class)
    public Map<String,String> handleBusinessException(MenuNotFoundException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RoleNotFoundException.class)
    public Map<String,String> handleBusinessException(RoleNotFoundException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }
}
