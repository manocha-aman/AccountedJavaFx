package com.uptech.accounted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.config.StageManager;
import com.uptech.accounted.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Controller
public class MenuBarController implements Initializable {
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

  public void showInitiatorForm(ActionEvent actionEvent) {
    stageManager.switchScene(FxmlView.INITIATOR);
  }

  public void showLedgerForm(ActionEvent actionEvent) {
	    stageManager.switchScene(FxmlView.LEDGER);
	  }

  public void showReceiverForm(ActionEvent actionEvent) {
	    stageManager.switchScene(FxmlView.RECEIVER);
	  }

  public void showTransactionForm(ActionEvent actionEvent) {
	    stageManager.switchScene(FxmlView.TRANSACTION);
	  }

  public void showUserForm(ActionEvent actionEvent) {
	    stageManager.switchScene(FxmlView.USER);
	  }

  public void showReportsForm(ActionEvent actionEvent) {
    stageManager.switchScene(FxmlView.REPORTS);
  }

}
