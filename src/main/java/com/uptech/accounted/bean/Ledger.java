package com.uptech.accounted.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Ledger")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ledger {

  @Id
  @Column(name = "ledgerCode", updatable = false, nullable = false)
  private String ledgerCode;

  @Column(name = "ledgerName", updatable = true, nullable = false)
  @NonNull
  private String ledgerName;

  @Enumerated(EnumType.STRING)
  private LedgerType ledgerType;

  public Ledger(String ledgerCode, String ledgerName, LedgerType ledgerType) {
    if (ledgerCode.isEmpty() || ledgerName.isEmpty() || ledgerType.name() == null)
      throw new IllegalArgumentException("Fields can't be left blank");

    this.ledgerCode = ledgerCode;
    this.ledgerName = ledgerName;
    this.ledgerType = ledgerType;
  }

}
