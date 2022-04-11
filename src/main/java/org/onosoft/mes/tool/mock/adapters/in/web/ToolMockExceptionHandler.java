package org.onosoft.mes.tool.mock.adapters.in.web;

import org.onosoft.mes.tool.mock.adapters.in.web.error.ApiError;
import org.onosoft.mes.tool.mock.domain.exception.LoadportFullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ToolMockExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(LoadportFullException.class)
  protected ResponseEntity<Object> handleLoadportFull(LoadportFullException ex) {
    ApiError err = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
    return new ResponseEntity<>(err, HttpStatus.CONFLICT)
  }



  protected ResponseEntity<Object> createResponse(ApiError error) {
    return new ResponseEntity<>(error, error.getHttpStatus());
  }
}
