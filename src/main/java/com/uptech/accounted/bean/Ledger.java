package com.uptech.accounted.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import lombok.Builder;
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
@Builder
public class Ledger {

  @Id
  @Column(name = "ledgerCode", updatable = false, nullable = false)
  @NonNull
  private String ledgerCode;
  @Column(name = "ledgerName", updatable = true, nullable = false)
  @NonNull
  private String ledgerName;

  public Ledger(String ledgerCode, String ledgerName) {
    this.ledgerCode = ledgerCode;
    this.ledgerName = ledgerName;
  }
  
  public static Ledger generateNewLedger(String ledgerCode, String ledgerName) {
    if (StringUtils.isEmpty(ledgerName) || StringUtils.isEmpty(ledgerCode)) {
      throw new IllegalArgumentException("Name and Code can't be blank/empty/null");
    }
    Ledger ledger = new Ledger();
    ledger.setLedgerCode(ledgerCode);
    ledger.setLedgerName(ledgerName);
    return ledger;
  }

}
