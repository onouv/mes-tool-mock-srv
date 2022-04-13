package org.onosoft.mes.tool.mock.adapters.in.web;

import org.onosoft.mes.tool.mock.adapters.in.web.error.ApiError;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ToolMockExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  protected ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
    ApiError err = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
    return new ResponseEntity<>(err, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleDefault(Exception ex) {
    ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  protected ResponseEntity<Object> createResponse(ApiError error) {
    return new ResponseEntity<>(error, error.getHttpStatus());
  }
}
