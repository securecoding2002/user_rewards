package com.example.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.assignment.AssignmentSpringTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@AssignmentSpringTest
@ActiveProfiles("dataloadtest")
public class DataLoaderTest {

  @Autowired UserService userService;
  @Autowired TransactionService transactionService;

  @Test
  public void loadData_generatesInitialData() {
    assertEquals(3, userService.findAllUsers().size());
  }
}
