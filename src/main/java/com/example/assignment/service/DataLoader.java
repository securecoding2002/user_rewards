package com.example.assignment.service;

import com.example.assignment.domain.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "data.load.enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class DataLoader {
  private final UserService userService;
  private final TransactionService transactionService;

  @Value("classpath:${data.load.user-input}")
  Resource userResourceFile;

  @Value("classpath:${data.load.transaction-input}")
  Resource transactionResourceFile;

  @EventListener(ApplicationReadyEvent.class)
  public void loadData() throws IOException {
    try (InputStreamReader userIsr = new InputStreamReader(userResourceFile.getInputStream());
        BufferedReader userBr = new BufferedReader(userIsr);
        InputStreamReader transactionIsr =
            new InputStreamReader(transactionResourceFile.getInputStream());
        BufferedReader transactionBr = new BufferedReader(transactionIsr)) {
      String line;
      while ((line = userBr.readLine()) != null) {
        String[] userDetails = line.split(",");
        if (userDetails.length == 2 && !userDetails[0].equals("userId")) {
          userService.addUser(
              User.builder().userId(userDetails[0].trim()).userName(userDetails[1].trim()).build());
        }
      }
      while ((line = transactionBr.readLine()) != null) {
        String[] transactionDetails = line.split(",");
        if (transactionDetails.length == 3 && !transactionDetails[0].equals("userId")) {
          transactionService.addTransactionByUserId(
              transactionDetails[0],
              Double.parseDouble(transactionDetails[1]),
              LocalDate.parse(transactionDetails[2], DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
        }
      }
    } catch (IOException e) {
      log.error("Error while loading data", e);
      throw e;
    }
  }
}
