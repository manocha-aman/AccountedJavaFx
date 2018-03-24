package com.uptech.accounted.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Receiver")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class Receiver implements Master {
	@Id
	@Column(name = "code", updatable = false, nullable = false)
	@NonNull
	private String code;
	@Column(name = "name", updatable = true, nullable = false)
	@NonNull
	private String name;
	
	public Receiver(String code, String name) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(code)) {
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
