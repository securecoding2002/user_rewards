package com.example.assignment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.assignment.AssignmentSpringTest;
import com.example.assignment.domain.User;
import com.example.assignment.entity.UserEntity;
import com.example.assignment.service.TransactionService;
import com.example.assignment.service.UserService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AssignmentSpringTest
@DirtiesContext
public class RewardControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired UserService userService;
  @Autowired TransactionService transactionService;

  @Test
  public void getRewards_NoUser_404Returned() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/rewards/not-present")
                .accept(RewardController.REWARDS_V1))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getRewards_UserWithNoTransaction_200ReturnedWithZeroTotal() throws Exception {
    User user = User.builder().userId("newId").userName("name").build();
    userService.addUser(user);
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/rewards/newId").accept(RewardController.REWARDS_V1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalRewards").value(0.0));
  }

  @Test
  public void getRewards_UserWithNoTransaction_200ReturnedWithValidTotal() throws Exception {
    User user = User.builder().userId("123").userName("name").build();
    UserEntity persistedUser = userService.addUser(user);
    transactionService.addTransactionByUserId(persistedUser.getUserId(), 200d, LocalDate.now());
    transactionService.addTransactionByUserId(persistedUser.getUserId(), 500.3d, LocalDate.now());
    mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/rewards/123").accept(RewardController.REWARDS_V1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalRewards").value(1100.6));
  }
}
