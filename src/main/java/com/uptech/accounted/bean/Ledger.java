package com.uptech.accounted.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

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
  @NonNull
  private String ledgerCode;

  @Column(name = "ledgerName", updatable = true, nullable = false)
  @NonNull
  private String ledgerName;

  @OneToMany(fetch=FetchType.EAGER, targetEntity=Subledger.class, cascade=CascadeType.ALL)
  @JoinColumn(name = "ledgerCode", referencedColumnName="ledgerCode")
  private Set<Subledger> subledgerList = new HashSet<>();

  public static Ledger generateNewLedger(String ledgerCode, String ledgerName, Set<Subledger> subledgerList) {
    if (StringUtils.isEmpty(ledgerName) || StringUtils.isEmpty(ledgerCode)) {
      throw new IllegalArgumentException("Name and Code can't be blank/empty/null");
    }
    Ledger ledger = new Ledger();
    ledger.setLedgerCode(ledgerCode);
    ledger.setLedgerName(ledgerName);
    ledger.setSubledgerList(subledgerList);
    return ledger;
  }

}
