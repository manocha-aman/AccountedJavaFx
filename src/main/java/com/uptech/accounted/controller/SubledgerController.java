package com.uptech.accounted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Subledger;
import com.uptech.accounted.service.SubledgerServiceImpl;
import com.uptech.accounted.validations.MasterValidationAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class SubledgerController implements Initializable {

  @FXML
  TextField subledgerCode;
  @FXML
  TextField subledgerName;
  @FXML
  private TableView<Subledger> subledgerTable;
  @FXML
  private TableColumn<Subledger, Long> colSubledgerId;
  @FXML
  private TableColumn<Subledger, String> colSubledgerCode;
  @FXML
  private TableColumn<Subledger, String> colSubledgerName;
  private ObservableList<Subledger> subledgerList = FXCollections.observableArrayList();
  @Autowired
  private MasterValidationAlert masterValidationAlert;
  @Autowired
  public SubledgerServiceImpl subledgerServiceImpl;

  public SubledgerController(@Autowired SubledgerServiceImpl service) {
  }

  private Subledger createNewSubledger() {
    return Subledger.generateNewSubledger(subledgerCode.getText(), subledgerName.getText());
  }

  public void reset(ActionEvent actionEvent) {
    clearFields();
  }

  private void clearFields() {
    subledgerCode.clear();
    subledgerName.clear();
  }

  public void save(ActionEvent actionEvent) {
    Subledger subledger = null;
    try {
      subledger = createNewSubledger();
    } catch (IllegalArgumentException illegalArgumentException) {
      masterValidationAlert.validationAlert(illegalArgumentException.getMessage());
    }

    subledgerServiceImpl.save(subledger);
    clearFields();
    loadDetails();
  }

  private void loadDetails() {
    subledgerList.clear();
    subledgerList.addAll(subledgerServiceImpl.findAll());
    subledgerTable.setItems(subledgerList);
    subledgerTable.setVisible(true);
  }

  public void delete(ActionEvent actionEvent) {
    Subledger selectedItem = subledgerTable.getSelectionModel().getSelectedItem();
    subledgerServiceImpl.delete(selectedItem);
    loadDetails();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    subledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    subledgerTable.setEditable(false);
    setColumnProperties();
    loadDetails();
  }

  private void setColumnProperties() {
    colSubledgerId.setCellValueFactory(new PropertyValueFactory<>("subledgerId"));
    colSubledgerName.setCellValueFactory(new PropertyValueFactory<>("subledgerName"));
    colSubledgerCode.setCellValueFactory(new PropertyValueFactory<>("subledgerCode"));
  }

}
