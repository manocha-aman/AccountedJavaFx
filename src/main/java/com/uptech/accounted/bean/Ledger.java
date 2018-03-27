package com.uptech.accounted.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ledgerId", updatable = false, nullable = false)
  private long ledgerId;
  @Column(name = "ledgerCode", updatable = false, nullable = false)
  @NonNull
  private String ledgerCode;
  @Column(name = "ledgerName", updatable = true, nullable = false)
  @NonNull
  private String ledgerName;
  @Column(name = "subLedgerCode", updatable = false, nullable = false)
  @NonNull
  private String subLedgerCode;
  @Column(name = "subLedgerName", updatable = true, nullable = false)
  @NonNull
  private String subLedgerName;

  public Ledger(long ledgerId, String ledgerCode, String ledgerName, String subLedgerCode, String subLedgerName) {
    this.ledgerId = ledgerId;
    this.ledgerCode = ledgerCode;
    this.ledgerName = ledgerName;
    this.subLedgerCode = subLedgerCode;
    this.subLedgerName = subLedgerName;
  }
  
  public static Ledger generateNewLedger(String ledgerCode, String ledgerName, String subLedgerCode, String subLedgerName) {
    if (StringUtils.isEmpty(ledgerName) || StringUtils.isEmpty(ledgerCode) || StringUtils.isEmpty(subLedgerName)
        || StringUtils.isEmpty(subLedgerCode)) {
      throw new IllegalArgumentException("Name and Code can't be blank/empty/null");
    }
    Ledger ledger = new Ledger();
    ledger.setLedgerCode(ledgerCode);
    ledger.setLedgerName(ledgerName);
    ledger.setSubLedgerCode(subLedgerCode);
    ledger.setSubLedgerName(subLedgerName);
    return ledger;
  }

}
