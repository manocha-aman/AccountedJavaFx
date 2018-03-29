package com.uptech.accounted.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "Subledger")
@Getter
@Setter
@NoArgsConstructor
public class Subledger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subledgerId", updatable = false, nullable = false)
  private long subledgerId;

  @Column(name = "subledgerCode", updatable = false, nullable = false)
  private long subledgerCode;

  @NonNull
  @Column(name = "subledgerName", updatable = true, nullable = false)
  private String subledgerName;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ledger")
  private Ledger ledger;

  public Subledger(long subledgerCode, String subledgerName) {
    super();
    this.subledgerCode = subledgerCode;
    this.subledgerName = subledgerName;
  }

}
