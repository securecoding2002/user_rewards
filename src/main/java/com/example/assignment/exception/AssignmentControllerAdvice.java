package com.example.assignment.exception;

import io.micrometer.core.instrument.MeterRegistry;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class AssignmentControllerAdvice {
  private final MeterRegistry meterRegistry;

  @ExceptionHandler({CustomerNotFoundException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(
      CustomerNotFoundException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());
    meterRegistry.counter("resource.not_found.counter").increment();
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}
