package com.example.assignment.controller;

import com.example.assignment.domain.User;
import com.example.assignment.service.UserService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserService userService;
  public static final String USER_V1 = "application/vnd.user.v1.hal+json";

  @GetMapping(produces = USER_V1)
  @Timed
  @Operation(description = "returns all users")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok().body(userService.findAllUsers());
  }
}
