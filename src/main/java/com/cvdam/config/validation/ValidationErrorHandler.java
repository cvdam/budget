package com.cvdam.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormError> handle(MethodArgumentNotValidException exception) {
		
		List<FormError> formErrors = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
						
		fieldErrors.forEach(err -> {
			String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
			FormError error = new FormError(err.getField(),message);
			formErrors.add(error);
		});
		return formErrors;
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public String handleStatus(ResponseStatusException exception) {

		return exception.getMessage();
		
	}
	
	
	
	
}
