package com.uptech.accounted.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "ledger")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Subledger> subledgerList = new HashSet<>();

  public Ledger(long ledgerCode, String ledgerName) {
    super();
    this.ledgerCode = ledgerCode;
    this.ledgerName = ledgerName;
  }

}
