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
  public String ledgerCode;

  @Column(name = "subledgerCode")
  public String  subledgerCode;

  public SubledgerId(String ledgerCode, String subledgerCode) {
    super();
    if(ledgerCode.isEmpty() || subledgerCode.isEmpty())
      throw new IllegalArgumentException("Fields can't be left blank");
    this.ledgerCode = ledgerCode;
    this.subledgerCode = subledgerCode;
  }
}