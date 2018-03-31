package com.uptech.accounted.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
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

  public Ledger(String ledgerCode, String ledgerName) {
    super();
    if(ledgerCode.isEmpty() || ledgerName.isEmpty())
      throw new IllegalArgumentException("Fields can't be left blank");

    this.ledgerCode = ledgerCode;
    this.ledgerName = ledgerName;
  }

}
