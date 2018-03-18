package com.codetreatise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Department")
public class Department implements Master {
  @Id
  @Column(name = "code", updatable = false, nullable = false)
  private String code;
  @Column(name = "name", updatable = true, nullable = false)
  private String name;

  private Department() {
  }

  public Department(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
