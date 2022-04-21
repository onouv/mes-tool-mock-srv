package org.onosoft.mes.tool.mock.adapters.in.web.exception;

import org.onosoft.mes.tool.mock.adapters.in.web.dto.ApiError;
import org.onosoft.mes.tool.mock.domain.exception.ApplicationException;
import org.onosoft.mes.tool.mock.domain.exception.NoSuchToolFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ToolMockExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ApplicationException.class})
  protected ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
    return this.respondWithCONFLICT(ex);
  }

  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<Object> handleNoSuchElement(NoSuchElementException ex) {
    return this.respondWithCONFLICT(ex);
  }

  @ExceptionHandler(NoSuchToolFoundException.class)
  protected ResponseEntity<Object> handleNoSuchToolFound(NoSuchToolFoundException ex) {
    ApiError err = new ApiError(
        HttpStatus.NOT_FOUND,
        String.format("cannot find tool with id = %s", ex.getToolId()),
        ex);
    return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleDefault(Exception ex) {
    ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  protected ResponseEntity<Object> respondWithCONFLICT(Exception ex) {
    ApiError err = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), ex);
    return new ResponseEntity<>(err, HttpStatus.CONFLICT);
  }
}
