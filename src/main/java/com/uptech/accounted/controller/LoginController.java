package com.uptech.accounted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.config.StageManager;
import com.uptech.accounted.service.UserServiceImpl;
import com.uptech.accounted.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController implements Initializable {

  @FXML
  private Button btnLogin;

  @FXML
  private PasswordField password;

  @FXML
  private TextField username;

  @FXML
  private Label lblLogin;

  @Autowired
  private UserServiceImpl userService;

  @Lazy
  @Autowired
  private StageManager stageManager;

  @FXML
  private void login(ActionEvent event) {
    if (userService.authenticate(getUsername(), getPassword())) {
      stageManager.switchScene(FxmlView.TRANSACTION);

    } else {
      lblLogin.setText("Login Failed.");
    }
  }

  public String getPassword() {
    return password.getText();
  }

  public String getUsername() {
    return username.getText();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

}
