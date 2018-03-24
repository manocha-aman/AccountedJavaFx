package com.uptech.accounted.validations;

import org.springframework.stereotype.Component;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Component
public class MasterValidationAlert {

  public void validationAlert(String errorText) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Validation Error");
    alert.setHeaderText(null);
    alert.setContentText(errorText);
    alert.showAndWait();
  }

}
