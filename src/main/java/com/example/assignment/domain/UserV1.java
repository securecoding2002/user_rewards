package com.example.assignment.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserV1 {
  @NonNull private String userId;
  @NonNull private String userName;
}
