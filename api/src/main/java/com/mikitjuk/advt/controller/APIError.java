package com.mikitjuk.advt.controller;

import com.mikitjuk.advt.exception.ErrorMetaData;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@CommonsLog
public class APIError {
	public static final String DEFAULT_FORM_KEY = "Please fill the form correctly";

	private String message;
	private List<ErrorMetaData> errors;

	public APIError(String message) {
		this.message = message;
	}

	public APIError(String message, List<ErrorMetaData> errors) {
		this.message = message;
		this.errors = errors;
	}

	public APIError(Set<ConstraintViolation<?>> results) {
		this(results, DEFAULT_FORM_KEY);
	}

	public APIError(Set<ConstraintViolation<?>> results, String globalMessageTemplate) {
		message = globalMessageTemplate;
		errors = results.stream().map(c ->
				createErrorMetaData(
						c.getRootBeanClass().getName(),
						c.getPropertyPath().toString(),
						c.getMessage())
		).collect(Collectors.toList());
	}

	private ErrorMetaData createErrorMetaData(String entity, String field, String message) {
		return ErrorMetaData.builder()
				.entity(entity)
				.field(field)
				.message(message)
				.build();
	}
}
