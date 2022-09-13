package com.example.assignment.controller;

import com.example.assignment.AssignmentSpringTest;
import com.example.assignment.domain.UserV1;
import com.example.assignment.entity.UserEntity;
import com.example.assignment.service.TransactionService;
import com.example.assignment.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AssignmentSpringTest
@DirtiesContext
public class RewardControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;

    @Test
    public void getRewards_NoUser_404Returned() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/rewards/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRewards_UserWithNoTransaction_200ReturnedWithZeroTotal() throws Exception {
        UserV1 user = UserV1.builder().userId("123").userName("name").build();
        userService.addUser(user);
        mvc.perform( MockMvcRequestBuilders
                        .get("/rewards/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRewards").value( 0.0));
    }

    @Test
    public void getRewards_UserWithNoTransaction_200ReturnedWithValidTotal() throws Exception {
        UserV1 user = UserV1.builder().userId("123").userName("name").build();
        UserEntity persistedUser = userService.addUser(user);
        transactionService.addTransactionByUserId(persistedUser.getUserId(), 200d, LocalDate.now());
        transactionService.addTransactionByUserId(persistedUser.getUserId(), 500.3d, LocalDate.now());
        mvc.perform( MockMvcRequestBuilders
                        .get("/rewards/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRewards").value( 1100.6));
    }
}
