package com.uptech.accounted.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
  @Column(name = "transactionId", updatable = false, nullable = false)
  private long transactionId;

  @NonNull
  @OneToOne
  private Initiator initiator;

  @NonNull
  @OneToOne
  private Department department;

  @NonNull
  private LocalDate dateOfTransaction;

  @NonNull
  @OneToOne
  private Recipient recipient;

  @NonNull
  @OneToOne
  private Ledger ledgerType;

  @NonNull
  @OneToOne
  private Subledger subledgerType;

  @NonNull
  private BigDecimal amount;

  @NonNull
  private String narration;

  @NonNull
  @OneToOne
  private SubjectMatter subjectMatter;

  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  public String getDepartmentName() {
    return department.getName();
  }

  public String getInitiatorName() {
    return initiator.getName();
  }

  public String getLedgerName() {
    return ledgerType.getLedgerName();
  }

  public String getSubledgerName() {
    return subledgerType.getSubledgerName();
  }

  public String getRecipientName() {
    return recipient.getName();
  }

  public String getSubjectMatterName() {
    return subjectMatter.getName();
  }

}
