package com.example.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.assignment.AssignmentSpringTest;
import com.example.assignment.domain.UserV1;
import com.example.assignment.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

@AssignmentSpringTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class UserServiceTest {
  @Autowired UserService userService;

  @Test
  public void addUser_CreatesNewEntity() {
    UserV1 user = UserV1.builder().userId("id").userName("name").build();
    UserEntity persistedUser = userService.addUser(user);
    assertNotNull(persistedUser.getId());
    assertNotNull(persistedUser.getCreatedAt());
    assertNotNull(persistedUser.getUpdatedAt());
    assertEquals(user.getUserId(), persistedUser.getUserId());
    assertEquals(user.getUserName(), persistedUser.getUserName());
  }

  @Test
  public void addUser_DuplicateUserId_ThrowsException() {
    UserV1 user = UserV1.builder().userId("dup-id").userName("name1").build();
    userService.addUser(user);
    assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(user));
  }

  @Test
  public void findByUserId_ReturnsValidUser() {
    UserV1 user = UserV1.builder().userId("id1").userName("name1").build();
    userService.addUser(user);
    assertEquals(user.getUserId(), userService.findByUserId("id1").get().getUserId());
  }

  @Test
  public void findByUserId_NoMatching_ReturnEmpty() {
    assertTrue(userService.findByUserId("id2").isEmpty());
  }
}
