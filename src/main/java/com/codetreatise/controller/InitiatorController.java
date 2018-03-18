package com.codetreatise.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Initiator;
import com.codetreatise.repository.InitiatorRepository;
import com.codetreatise.service.InitiatorServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class InitiatorController implements Initializable {
  @FXML
  private Label lblInitiator;
  @FXML
  private Button reset;
  @FXML
  private Button saveInitiator;
  @FXML
  private TableColumn<Initiator, Long> colCode;
  @FXML
  private TableColumn<Initiator, String> colName;
  @FXML
  private MenuItem deleteInitiators;
  @FXML
  private TextField code;
  @FXML
  private TextField name;
  @Autowired
  private InitiatorRepository initiatorRepository;

  @Autowired
  private InitiatorServiceImpl initiatorService;

  private ObservableList<Initiator> initiatorList = FXCollections.observableArrayList();

  @FXML
  private TableView<Initiator> initiatorTable;

  public void reset(ActionEvent actionEvent) {
    clearFields();
  }

  private void clearFields() {
    name.clear();
    code.clear();
  }


  private void loadInitiatorDetails() {
    initiatorList.clear();
    initiatorList.addAll(initiatorService.findAll());
    initiatorTable.setItems(initiatorList);
  }

  public void updateInitiator(ActionEvent actionEvent) {
    // initiatorRepository.
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initiatorTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    setColumnProperties();
    loadInitiatorDetails();
  }

  private void setColumnProperties() {
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
  }

  @FXML
  private void saveInitiator(ActionEvent event) {


    Initiator initiator = new Initiator();
    initiator.setName(getName().getText());
    initiator.setCode(getCode().getText());

    Initiator newInitiator = initiatorService.save(initiator);
    clearFields();
    loadInitiatorDetails();

  }

  private void saveAlert(Initiator newInitiator) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Initiator saved successfully.");
    alert.setHeaderText(null);
    alert.setContentText("An initiator " + newInitiator.getName() + " has been created with code : "
                             + newInitiator.getCode() + ".");
    alert.showAndWait();
  }




  public TextField getCode() {
    return code;
  }

  public void setCode(TextField code) {
    this.code = code;
  }

  public TextField getName() {
    return name;
  }

  public void setName(TextField name) {
    this.name = name;
  }

  private void updateAlert(Initiator initiator) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Initiator updated successfully.");
    alert.setHeaderText(null);
    alert.setContentText("The initiator " + initiator.getName() + " has been updated.");
    alert.showAndWait();
  }
}