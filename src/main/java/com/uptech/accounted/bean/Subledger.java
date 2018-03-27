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
@Table(name = "Subledger")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class Subledger {

  @Id
  @NonNull
  @Column(name = "subledgerCode", updatable = false, nullable = false)
  private String subledgerCode;
  @NonNull
  @Column(name = "subledgerName", updatable = true, nullable = false)
  private String subledgerName;

  public Subledger(String subledgerCode, String subledgerName) {
    this.subledgerCode = subledgerCode;
    this.subledgerName = subledgerName;
  }

  public static Subledger generateNewSubledger(String subledgerCode, String subledgerName) {
    if (StringUtils.isEmpty(subledgerName) || StringUtils.isEmpty(subledgerCode)) {
      throw new IllegalArgumentException("Name and Code can't be blank/empty/null");
    }
    Subledger subledger = new Subledger();
    subledger.setSubledgerCode(subledgerCode);
    subledger.setSubledgerName(subledgerName);
    return subledger;
  }

}
