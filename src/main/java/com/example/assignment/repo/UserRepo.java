package com.example.assignment.repo;

import com.example.assignment.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
  @Query(value = "SELECT u FROM UserEntity u LEFT JOIN FETCH u.transactions where u.userId = ?1")
  Optional<UserEntity> findByUserId(String userId);
}
