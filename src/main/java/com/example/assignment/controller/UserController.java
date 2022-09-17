package com.example.assignment.controller;

import com.example.assignment.domain.UserV1;
import com.example.assignment.service.UserService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/users")
@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserService userService;

  @GetMapping
  @Timed
  @Operation(description = "returns all users")
  public ResponseEntity<List<UserV1>> getUsers() {
    return ResponseEntity.ok().body(userService.findAllUsers());
  }
}
