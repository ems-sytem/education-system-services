package com.education.system.api;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.education.system.error.ApiErrors;
import com.education.system.exception.InvalidRequestException;
import com.education.system.exception.ResourceNotFoundException;
import com.education.system.exception.UsernameAlreadyUsedException;
import com.education.system.models.ResponseMessage;

/**
 * Called when an exception occurs during request processing
 * Transforms exception message into JSON format
 */
@RestControllerAdvice()
public class RestExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Inject
	private MessageSource messageSource;

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	public ResponseEntity<ResponseMessage> handleGenericException(Exception ex, WebRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("handling exception...");
		}
		return new ResponseEntity<>(new ResponseMessage(ResponseMessage.Type.danger, ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	public ResponseEntity<ResponseMessage> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("handling ResourceNotFoundException...");
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { UsernameAlreadyUsedException.class })
	public ResponseEntity<ResponseMessage> handleUsernameExistedException(UsernameAlreadyUsedException ex,
			WebRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("handling UsernameExistedException...");
		}

		ResponseMessage error = ResponseMessage.danger("username existed.");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { InvalidRequestException.class })
	public ResponseEntity<ResponseMessage> handleInvalidRequestException(InvalidRequestException ex, WebRequest req) {
		if (log.isDebugEnabled()) {
			log.debug("handling InvalidRequestException...");
		}

		ResponseMessage alert = new ResponseMessage(ResponseMessage.Type.danger, ApiErrors.INVALID_REQUEST,
				messageSource.getMessage(ApiErrors.INVALID_REQUEST, new String[] {}, null));

		BindingResult result = ex.getErrors();

		List<FieldError> fieldErrors = result.getFieldErrors();

		if (!fieldErrors.isEmpty()) {
			fieldErrors.stream().forEach((e) -> {
				alert.addError(e.getField(), e.getCode(), e.getDefaultMessage());
			});
		}

		return new ResponseEntity<>(alert, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
