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
@Table(name = "Recipient")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class Recipient implements Master {
  @Id
  @Column(name = "code", updatable = false, nullable = false)
  @NonNull
  private String code;
  @Column(name = "name", updatable = true, nullable = false)
  @NonNull
  private String name;

  public Recipient(String code, String name) {
    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(code)) {
      throw new IllegalArgumentException("Name and Code can't be blank/empty/null");
    }
    this.code = code;
    this.name = name;
  }

}
