package com.example.assignment.service;

import com.example.assignment.entity.TransactionEntity;
import com.example.assignment.entity.UserEntity;
import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.repo.UserRepo;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.LocalDate;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
  private final UserService userService;
  private final MeterRegistry meterRegistry;
  private final UserRepo userRepo;

  /**
   * adds transaction for the user id.
   * @param userId user id
   * @param amount amount spent
   * @Thorws InvalidRequestException for invalid user
   */
  @Timed
  @Transactional
  public void addTransactionByUserId(@NonNull String userId, @NonNull Double amount, @NonNull LocalDate transactionDate) {
    Optional<UserEntity> userEntity = userService.findByUserId(userId);
    if (userEntity.isEmpty()) {
      meterRegistry.counter("transaction.user.error").increment();
      String message = String.format("User %s not present in DB", userId);
      log.error("{}", message);
      throw new ResourceNotFoundException(message);
    }
    userEntity
        .get()
        .getTransactions()
        .add(TransactionEntity.builder().transactionDate(transactionDate).amount(amount)
                .rewards(calculateRewards(amount))
                .build());
    userRepo.save(userEntity.get());
  }

  /**
   * calculates rewards for the amount spent. Returns 0 for any negative amount
   * @param price input prices
   * @return rewards
   */
  public Double calculateRewards(Double price) {
    if (price > 100) {
      return Math.round((2 * (price - 100) + 50)* 100 )/ 100.0d;
    } else if(price > 50) {
      return Math.round(price - 50) * 100 / 100d;
    }
    return 0.00;
  }
}
