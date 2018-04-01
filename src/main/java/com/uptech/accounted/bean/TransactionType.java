package com.uptech.accounted.bean;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum TransactionType {
  INCOME, EXPENSE;

  private BigDecimal multiplier;

  static {
    INCOME.multiplier = new BigDecimal(1);
    EXPENSE.multiplier = new BigDecimal(-1);
  }

  public BigDecimal getMultiplier() {
    return multiplier;

  }
}