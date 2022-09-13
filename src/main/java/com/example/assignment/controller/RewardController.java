package com.example.assignment.controller;

import com.example.assignment.domain.RewardSummaryV1;
import com.example.assignment.service.RewardsService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/rewards")
@RequiredArgsConstructor
@RestController
public class RewardController {
    private final RewardsService rewardsService;

    @GetMapping(path = "/{userId}")
    @Timed
    @Operation(description = "loads rewards for given user id")
    public ResponseEntity<RewardSummaryV1> getRewardSummary(@PathVariable String userId) {
        return ResponseEntity.ok().body(rewardsService.getRewards(userId));
    }

}
