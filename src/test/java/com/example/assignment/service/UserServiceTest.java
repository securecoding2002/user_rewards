package com.example.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.assignment.AssignmentSpringTest;
import com.example.assignment.domain.UserV1;
import com.example.assignment.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

@AssignmentSpringTest
@DirtiesContext
public class UserServiceTest {
  @Autowired UserService userService;

  @Test
  public void testSaveUser_CreatesNewEntity() {
    UserV1 user = UserV1.builder().userId("id").userName("name").build();
    UserEntity persistedUser = userService.addUser(user);
    assertNotNull(persistedUser.getId());
    assertNotNull(persistedUser.getCreatedAt());
    assertNotNull(persistedUser.getUpdatedAt());
    assertEquals(user.getUserId(), persistedUser.getUserId());
    assertEquals(user.getUserName(), persistedUser.getUserName());
  }

  @Test
  public void testSaveUser_DuplicateUserId_ThrowsException() {
    UserV1 user = UserV1.builder().userId("id1").userName("name1").build();
    userService.addUser(user);
    assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(user));
  }
}
