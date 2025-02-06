package com.pets.config;

import com.pets.utils.base.BaseErrorException;
import com.pets.utils.base.ResponseData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * Ajax的请求异常处理
     */
    private ResponseData handleAjaxRequestException(Throwable e, int code) {
        ResponseData responseDto = new ResponseData(code, e.getMessage());
        return responseDto;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseData handleException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return handleAjaxRequestException(new Throwable(fieldErrors.stream().map(m -> m.getDefaultMessage()).collect(Collectors.joining(","))), 400);
    }

    /**
     * 自定义异常 BaseErrorException
     */
    @ResponseBody
    @ExceptionHandler(value = BaseErrorException.class)
    public ResponseData handleBaseException(HttpServletRequest request, HttpServletResponse response, BaseErrorException e) {
        log.info(String.format("[业务异常]：%s", e.getMessage()));
        return handleAjaxRequestException(e, e.getCode());
    }
}
