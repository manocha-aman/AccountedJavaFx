package com.uptech.accounted.controller;

import com.uptech.accounted.bean.Initiator;
import com.uptech.accounted.service.InitiatorServiceImpl;
import com.uptech.accounted.service.ReportsService;
import com.uptech.accounted.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class ReportsController implements Initializable {
  private ObservableList<String> list = FXCollections.observableArrayList();

  @FXML
  private ListView<String> lvInitiators;
  @Autowired
  private ReportsService reportsService;
  @Autowired
  private InitiatorServiceImpl initiatorService;
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    load();
  }

  private void load() {
    lvInitiators.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    list.clear();
    list.addAll(initiatorService.findAll().stream().map(Initiator::getName).collect(Collectors.toList()));
    System.out.println(list);
    lvInitiators.setItems(list);
    lvInitiators.setVisible(true);
  }

  public void generateReport(ActionEvent actionEvent) {

    //reportsService.findBySpecs();
  }
}
