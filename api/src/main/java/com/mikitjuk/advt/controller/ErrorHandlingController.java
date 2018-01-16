package com.mikitjuk.advt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ErrorHandlingController {

	@ExceptionHandler({AuthenticationException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public void login(HttpServletRequest req, AuthenticationException ex) {
		log.info("Someone trying to login with ip: {}", req.getRemoteAddr());
	}

	@ExceptionHandler({MissingServletRequestParameterException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public void pathException(MissingServletRequestParameterException ex) {
		log.debug(ex.getMessage(), ex);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void handleEntityNotFoundException(EntityNotFoundException exception) {
		log.warn(exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public void handleBindException(BindException exception) {
		log.warn(exception.getMessage());
	}

	@ExceptionHandler({AccessDeniedException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public void accessDeniedHandler(AccessDeniedException ex) {
		log.error(ex.getMessage(), ex);
	}
}
