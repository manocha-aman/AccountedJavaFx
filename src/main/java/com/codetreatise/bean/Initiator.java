package com.codetreatise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Initiator")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Initiator {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private long id;
  private String code;
  private String name;

  public Initiator(long id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

}