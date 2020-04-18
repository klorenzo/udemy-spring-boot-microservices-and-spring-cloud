package com.kevinlorenzo.mobile.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kevinlorenzo.mobile.app.ws.models.response.ErrorResponse;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception e, WebRequest webRequest) {
		String exceptionMessage = e.getLocalizedMessage();

		if (exceptionMessage == null) {
			exceptionMessage = e.toString();
		}

		ErrorResponse errorResponse = new ErrorResponse(new Date(), exceptionMessage);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { NullPointerException.class, UserServiceException.class })
	public ResponseEntity<Object> handleNullPointerException(Exception e, WebRequest webRequest) {
		String exceptionMessage = e.getMessage();

		if (exceptionMessage == null) {
			exceptionMessage = e.toString();
		}

		ErrorResponse errorResponse = new ErrorResponse(new Date(), exceptionMessage);

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
