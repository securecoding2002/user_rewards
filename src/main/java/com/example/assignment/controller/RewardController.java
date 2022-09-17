package com.example.assignment.controller;

import com.example.assignment.domain.RewardSummary;
import com.example.assignment.service.RewardsService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/v1/rewards")
@RequiredArgsConstructor
@RestController
public class RewardController {
  private final RewardsService rewardsService;
  public static final String REWARDS_V1 = "application/vnd.rewards.v1.hal+json";

  @GetMapping(path = "/{userId}", produces = REWARDS_V1)
  @Timed
  @Operation(description = "loads rewards for given user id")
  public ResponseEntity<RewardSummary> getRewardSummary(@PathVariable String userId) {
    return ResponseEntity.ok().body(rewardsService.getRewards(userId));
  }
}
