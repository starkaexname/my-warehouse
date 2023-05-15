package info.riabokon.mywarehouse.controller;

import info.riabokon.mywarehouse.util.ErrorInfoDTO;
import info.riabokon.mywarehouse.util.TimeRangeException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(TimeRangeException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfoDTO timeRangeError(HttpServletRequest req, TimeRangeException e) {
        return logAndGetErrorInfo(req, e);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfoDTO methodArgumentTypeMismatchError(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
        return logAndGetErrorInfo(req, e);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfoDTO missingServletRequestParameterError(HttpServletRequest req, MissingServletRequestParameterException e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(EntityNotFoundException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfoDTO missingEntityByIdError(HttpServletRequest req, EntityNotFoundException e) {
        return logAndGetErrorInfo(req, e);
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(Exception.class)
    public ErrorInfoDTO handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e);
    }

    private ErrorInfoDTO logAndGetErrorInfo(HttpServletRequest req, Exception e) {
        log.error("Exception at request " + req.getRequestURL(), e);
        return new ErrorInfoDTO(req.getRequestURL(), e);
    }
}
