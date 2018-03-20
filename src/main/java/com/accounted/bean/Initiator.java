package com.accounted.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.h2.util.StringUtils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.Builder;
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
@Builder
public class Initiator implements Master {
  @Id
  @Column(name = "code", updatable = false, nullable = false)
  @NonNull private String code;
  @Column(name = "name", updatable = true, nullable = false)
  @NonNull private String name;

  public Initiator(String code, String name) {
	  if(StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(code)) {
	      validationAlert();  
		  throw new IllegalArgumentException("Name and Code can't be blank/empty/null"); 
	    }
    this.code = code;
    this.name = name;
  }

	private void validationAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		alert.setContentText("Please Enter Valid name/code");
		alert.showAndWait();
	}
}
