package com.uptech.accounted.bean;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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

  @EmbeddedId
  @AttributeOverrides({ 
      @AttributeOverride(name = "ledgerCode", column = @Column(name = "ledgerCode")),
      @AttributeOverride(name = "subledgerCode", column = @Column(name = "subledgerCode")) 
      })
  @Column(name = "subledgerId")
  private SubledgerId subledgerId;

  @NonNull
  @Column(name = "subledgerName", updatable = true, nullable = false)
  private String subledgerName;

  @ManyToOne
  @MapsId("ledgerCode")
  @JoinColumn(name = "ledgerCode")
  private Ledger ledger;

  public Subledger(SubledgerId subledgerId, String subledgerName) {
    super();
    this.subledgerId = subledgerId;
    this.subledgerName = subledgerName;
  }

}
