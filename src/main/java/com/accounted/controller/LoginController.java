package com.accounted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.accounted.config.StageManager;
import com.accounted.service.UserServiceImpl;
import com.accounted.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

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
      stageManager.switchScene(FxmlView.TRANSACTION);
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
