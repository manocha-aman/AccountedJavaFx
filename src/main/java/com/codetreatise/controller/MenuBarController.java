package com.codetreatise.controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MenuBarController  implements Initializable {
  @Lazy
  @Autowired
  private StageManager stageManager;
  @FXML
  private void exit(ActionEvent event) {
    Platform.exit();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void showDepartmentForm(ActionEvent actionEvent) {
    stageManager.switchScene(FxmlView.DEPARTMENT);
  }
}

