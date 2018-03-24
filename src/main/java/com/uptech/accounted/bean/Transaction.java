package com.uptech.accounted.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Transaction")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private long id;

  @NonNull private String initiator;

  @NonNull private String department;

  @NonNull private LocalDate dateOfTransaction;

  @NonNull private String receiver;

  @NonNull private String LedgerType;

  @NonNull private BigDecimal amount;

  @NonNull private String subjectMatter;

}
