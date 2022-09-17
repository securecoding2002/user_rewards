package com.example.assignment.domain;

import java.time.YearMonth;
import java.util.TreeMap;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RewardSummaryV1 {
  private String userId;
  private YearMonth startDate;
  private YearMonth endDate;
  private Double totalRewards;
  private TreeMap<YearMonth, Double> rewardDetails;
}
