package com.example.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.assignment.AssignmentSpringTest;
import com.example.assignment.domain.User;
import com.example.assignment.service.TransactionService;
import com.example.assignment.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AssignmentSpringTest
@DirtiesContext
public class UserControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired UserService userService;
  @Autowired TransactionService transactionService;

  @Autowired ObjectMapper objectMapper;

  @Test
  public void getUsers_NoUser_EmptyListReturned() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/v1/users").accept(UserController.USER_V1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  public void getUsers_ListReturned() throws Exception {
    User user = User.builder().userId("id1").userName("name1").build();
    userService.addUser(user);
    User user1 = User.builder().userId("id2").userName("name2").build();
    userService.addUser(user1);
    MvcResult result =
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users").accept(UserController.USER_V1))
            .andExpect(status().isOk())
            .andReturn();

    List<User> returnedUsers =
        objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
    assertEquals(List.of(user, user1), returnedUsers);
  }
}
