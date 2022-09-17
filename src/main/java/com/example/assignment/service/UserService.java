package com.example.assignment.service;

import com.example.assignment.domain.UserV1;
import com.example.assignment.entity.UserEntity;
import com.example.assignment.repo.UserRepo;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepo userRepo;
  ModelMapper modelMapper = new ModelMapper();

  /**
   * Register new user with DB
   *
   * @param user {@link UserV1} user details
   * @return {@link UserEntity}
   */
  @Transactional
  @Timed
  public UserEntity addUser(UserV1 user) {
    log.debug("adding new user with user id {}", user.getUserId());
    return userRepo.save(
        UserEntity.builder().userId(user.getUserId()).userName(user.getUserName()).build());
  }

  /**
   * finds user in the DB
   *
   * @param userId user id
   * @return Optional entity
   */
  @Timed
  @Transactional(readOnly = true)
  public Optional<UserEntity> findByUserId(String userId) {
    return userRepo.findByUserId(userId);
  }

  /**
   * list all users in the DB
   *
   * @return List of users
   */
  @Timed
  @Transactional(readOnly = true)
  public List<UserV1> findAllUsers() {
    return userRepo.findAll().stream()
        .map(userEntity -> modelMapper.map(userEntity, UserV1.class))
        .collect(Collectors.toList());
  }
}
