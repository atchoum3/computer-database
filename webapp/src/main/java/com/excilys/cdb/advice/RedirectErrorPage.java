package com.excilys.cdb.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RedirectErrorPage {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest req, Exception ex) {
    	return "404";
    }

    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest req, Exception ex) {
    	return "500";
    }

}