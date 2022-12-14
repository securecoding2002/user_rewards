package com.example.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.assignment.AssignmentSpringTest;
import com.example.assignment.domain.RewardSummary;
import com.example.assignment.entity.TransactionEntity;
import com.example.assignment.entity.UserEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@AssignmentSpringTest
public class RewardServiceTest {
  @MockBean @Autowired UserService userService;
  @Autowired TransactionService transactionService;
  @Autowired RewardsService rewardsService;

  @Test
  public void getRewards_NoTransaction_ReturnEmptyRewards() {
    UserEntity userEntity = UserEntity.builder().userId("id").userName("name").build();
    when(userService.findByUserId("id")).thenReturn(Optional.of(userEntity));
    RewardSummary summary = rewardsService.getRewards("id");
    assertEquals(0, summary.getTotalRewards());
    assertEquals(userEntity.getUserId(), summary.getUserId());
  }

  @Test
  public void getRewards_WithTransaction_ReturnRewards() {
    List<TransactionEntity> transactions =
        List.of(
            TransactionEntity.builder()
                .amount(200d)
                .rewards(transactionService.calculateRewards(200d))
                .transactionDate(LocalDate.now())
                .build(),
            TransactionEntity.builder()
                .amount(20d)
                .rewards(transactionService.calculateRewards(20d))
                .transactionDate(
                    LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().minus(1), 1))
                .build());
    UserEntity userEntity =
        UserEntity.builder().userId("id").userName("name").transactions(transactions).build();
    when(userService.findByUserId("id")).thenReturn(Optional.of(userEntity));
    RewardSummary summary = rewardsService.getRewards("id");
    assertEquals(250.0, summary.getTotalRewards());
    assertEquals(userEntity.getUserId(), summary.getUserId());
  }
}
