package com.mikitjuk.advt.controller;

import com.mikitjuk.advt.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

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

	@ExceptionHandler({ForbiddenException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public void forbiddenHandler(ForbiddenException ex) {
		log.error(ex.getMessage(), ex);
	}

	@ExceptionHandler({AccessDeniedException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public void accessDeniedHandler(AccessDeniedException ex) {
		log.error(ex.getMessage(), ex);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<APIError> exception(ConstraintViolationException ex) {
		log.error(ex.getMessage(), ex);
		if (CollectionUtils.isEmpty(ex.getConstraintViolations())) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(new APIError(ex.getConstraintViolations()));
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public APIError exception(ValidationException ex) {
		log.error(ex.getCause().getMessage(), ex.getCause());
		return new APIError(ex.getCause().getMessage());
	}

	@ExceptionHandler(ConversionException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public APIError conversionException(ConversionException ex) {
		return new APIError(ex.getMessage());
	}
}
