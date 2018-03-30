package com.uptech.accounted.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "Ledger")
@Getter
@Setter
@NoArgsConstructor
public class Ledger {

  @Id
  @Column(name = "ledgerCode", updatable = false, nullable = false)
  private long ledgerCode;

  @Column(name = "ledgerName", updatable = true, nullable = false)
  @NonNull
  private String ledgerName;

  public Ledger(long ledgerCode, String ledgerName) {
    super();
    this.ledgerCode = ledgerCode;
    this.ledgerName = ledgerName;
  }

}
