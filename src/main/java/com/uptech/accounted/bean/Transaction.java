package com.uptech.accounted.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  @Column(name = "transactionId", updatable = false, nullable = false)
  private long transactionId;

  @NonNull
  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Initiator initiator;

  @NonNull
  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Department department;

  @NonNull
  private LocalDate dateOfTransaction;

  @NonNull
  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Recipient recipient;

  @NonNull
  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Ledger LedgerType;

  @NonNull
  private BigDecimal amount;

  @NonNull
  private String subjectMatter;

}
