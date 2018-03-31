package com.uptech.accounted.bean;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum TransactionType {
  INCOME, EXPENSE;
  public static BigDecimal getMultiplier(TransactionType transactionType) {
    BigDecimal multiplier = transactionType.equals(TransactionType.EXPENSE) ? new BigDecimal(1) : new BigDecimal(-1);
    return multiplier;
  }
}
