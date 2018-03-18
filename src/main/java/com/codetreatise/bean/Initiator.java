package com.codetreatise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Initiator")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Initiator implements Master {
  @Id
  @Column(name = "code", updatable = false, nullable = false)
  @NonNull private String code;
  @Column(name = "name", updatable = true, nullable = false)
  @NonNull private String name;

  public Initiator(String code, String name) {
    this.code = code;
    this.name = name;
  }

}
