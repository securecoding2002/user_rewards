package com.example.assignment.service;

import com.example.assignment.domain.RewardSummary;
import com.example.assignment.entity.TransactionEntity;
import com.example.assignment.entity.UserEntity;
import com.example.assignment.exception.CustomerNotFoundException;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.YearMonth;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardsService {
  private final UserService userService;
  private final MeterRegistry meterRegistry;

  /**
   * generates monthly rewards summary total
   *
   * @param userId user id for reward summary
   * @return {@link RewardSummary} Monthly group reward summary
   */
  public RewardSummary getRewards(String userId) {
    Optional<UserEntity> user = userService.findByUserId(userId);
    if (user.isEmpty()) {
      meterRegistry.counter("transaction.user.error").increment();
      String message = String.format("User %s not present in DB", userId);
      log.error("{}", message);
      throw new CustomerNotFoundException(message);
    }
    TreeMap<YearMonth, Double> rewardsByMonth =
        user.get().getTransactions().parallelStream()
            .collect(
                Collectors.groupingBy(
                    t -> YearMonth.from(t.getTransactionDate()),
                    TreeMap::new,
                    Collectors.summingDouble(TransactionEntity::getRewards)));

    RewardSummary summary =
        RewardSummary.builder()
            .userId(userId)
            .rewardDetails(rewardsByMonth)
            .totalRewards(0.0)
            .build();
    if (!rewardsByMonth.isEmpty()) {
      summary.setStartDate(rewardsByMonth.firstKey());
      summary.setEndDate(rewardsByMonth.lastKey());
      summary.setTotalRewards(
          rewardsByMonth.values().parallelStream().mapToDouble(Double::doubleValue).sum());
    }
    return summary;
  }
}
