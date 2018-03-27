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
@Table(name = "Subsubledger")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class Subledger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subledgerId", updatable = false, nullable = false)
  private long subledgerId;
  @Column(name = "subledgerCode", updatable = false, nullable = false)
  @NonNull
  private String subledgerCode;
  @Column(name = "subledgerName", updatable = true, nullable = false)
  @NonNull
  private String subledgerName;

  public Subledger(long subledgerId, String subledgerCode, String subledgerName) {
    this.subledgerId = subledgerId;
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
