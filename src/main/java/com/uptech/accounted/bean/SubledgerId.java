package com.uptech.accounted.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubledgerId implements Serializable {

  private static final long serialVersionUID = 1L;
  @Column(name = "ledgerCode")
  public long ledgerCode;

  @Column(name = "subledgerCode")
  public long subledgerCode;

  public SubledgerId(long ledgerCode, long subledgerCode) {
    super();
    this.ledgerCode = ledgerCode;
    this.subledgerCode = subledgerCode;
  }
}