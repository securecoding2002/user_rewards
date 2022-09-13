package com.example.assignment.service;

import com.example.assignment.domain.RewardSummaryV1;
import com.example.assignment.entity.TransactionEntity;
import com.example.assignment.entity.UserEntity;
import com.example.assignment.exception.ResourceNotFoundException;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardsService {
    private final UserService userService;
    private final MeterRegistry meterRegistry;

    /**
     * generates  monthly rewards summary total
     * @param userId user id for reward summary
     * @return {@link RewardSummaryV1} Monthly group reward summary
     */
    public RewardSummaryV1 getRewards(String userId) {
       Optional<UserEntity> user = userService.findByUserId(userId);
       if(user.isEmpty()) {
           meterRegistry.counter("transaction.user.error").increment();
           String message = String.format("User %s not present in DB", userId);
           log.error("{}", message);
           throw new ResourceNotFoundException(message);
       }
        TreeMap<YearMonth, Double> rewardsByMonth = user.get().getTransactions().stream()
                .collect(Collectors.groupingBy(t -> YearMonth.from(t.getTransactionDate()),
                        TreeMap::new,
                        Collectors.summingDouble(TransactionEntity::getRewards)));

       return RewardSummaryV1.builder()
               .userId(userId)
               .rewardDetails(rewardsByMonth)
               .build();

    }
}
