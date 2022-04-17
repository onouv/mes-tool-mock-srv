package org.onosoft.mes.tool.mock.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class ApiError {
  protected HttpStatus httpStatus;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
  protected LocalDateTime timestamp;
  protected String applicationMessage;
  protected String debugMessage;

  private ApiError() {
    this.timestamp = LocalDateTime.now();
  }

  public ApiError(HttpStatus status) {
    this();
    this.httpStatus = status;
  }

  public ApiError(HttpStatus status, Throwable exception) {
    this.httpStatus = status;
    this.applicationMessage = "Unknown Error.";
    this.debugMessage = exception.getLocalizedMessage();
  }

  public ApiError(HttpStatus status, String applicationMessage, Throwable exception) {
    this.httpStatus = status;
    this.applicationMessage = applicationMessage;
    this.debugMessage = exception.getLocalizedMessage();
  }
}
