package com.example.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.assignment.AssignmentSpringTest;
import com.example.assignment.domain.UserV1;
import com.example.assignment.entity.UserEntity;
import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.repo.UserRepo;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

@AssignmentSpringTest
@DirtiesContext
public class TransactionServiceTest {
  @Autowired UserService userService;
  @Autowired TransactionService transactionService;
  @Autowired UserRepo userRepo;

  @Test
  public void addTransaction_ExistingUser_TransactionAdded() {
    UserV1 user = UserV1.builder().userId("id").userName("name").build();
    UserEntity persistedUser = userService.addUser(user);
    transactionService.addTransactionByUserId(persistedUser.getUserId(), 200d, LocalDate.now());
    assertEquals(
        1, userService.findByUserId(persistedUser.getUserId()).get().getTransactions().size());
    // add another transaction
    transactionService.addTransactionByUserId(persistedUser.getUserId(), 500.3d, LocalDate.now());
    assertEquals(
        2, userService.findByUserId(persistedUser.getUserId()).get().getTransactions().size());
  }

  @Test
  public void addTransaction_UserNotPresent_ExceptionThrown() {
    assertThrows(
        ResourceNotFoundException.class,
        () -> transactionService.addTransactionByUserId("not_present", 200d, LocalDate.now()));
  }

  @Test
  public void calculateRewards_ReturnsRewards() {
    // Map of amount vs expected rewards for validation
    Map<Double, Double> testData =
        Map.of(
            100d, 50d,
            100.20d, 50.4d,
            0d, 0d,
            50d, 0d,
            101d, 52d,
            -10d, 0d,
            420.60d, 691.2d);

    testData.forEach((k, v) -> assertEquals(v, transactionService.calculateRewards(k)));
  }
}
