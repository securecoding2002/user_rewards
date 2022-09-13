package com.example.assignment.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
public class RewardSummaryV1 {
    private String userId;
    private YearMonth startDate;
    private YearMonth endDate;
    private Double totalRewards;
    private TreeMap<YearMonth, Double> rewardDetails;

    public YearMonth getStartDate() {
        return rewardDetails.firstKey();
    }

    public YearMonth getEndDate() {
        return rewardDetails.lastKey();
    }

    public Double getTotalRewards() {
        return rewardDetails.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
