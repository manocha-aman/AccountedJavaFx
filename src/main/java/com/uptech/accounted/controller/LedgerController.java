package com.uptech.accounted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.bean.Subledger;
import com.uptech.accounted.service.LedgerServiceImpl;
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
public class LedgerController implements Initializable {

  @FXML
  TextField ledgerCode;
  @FXML
  TextField ledgerName;
  @FXML
  TextField subledgerCode;
  @FXML
  TextField subledgerName;
  @FXML
  private TableView<Ledger> ledgerTable;
  @FXML
  private TableView<Subledger> subledgerTable;
  @FXML
  private TableColumn<Ledger, String> colLedgerCode;
  @FXML
  private TableColumn<Ledger, String> colLedgerName;
  private ObservableList<Ledger> ledgerList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<Subledger, String> colSubledgerCode;
  @FXML
  private TableColumn<Subledger, String> colSubledgerName;
  private ObservableList<Subledger> subledgerList = FXCollections.observableArrayList();
  @Autowired
  private MasterValidationAlert masterValidationAlert;
  @Autowired
  public LedgerServiceImpl ledgerServiceImpl;
  @Autowired
  public SubledgerServiceImpl subledgerServiceImpl;

  public LedgerController(@Autowired LedgerServiceImpl service) {
  }

  private Ledger createNewLedger() {
    return Ledger.generateNewLedger(ledgerCode.getText(), ledgerName.getText());
  }

  private Subledger createNewSubledger() {
    return Subledger.generateNewSubledger(subledgerCode.getText(), subledgerName.getText());
  }

  public void reset(ActionEvent actionEvent) {
    clearFields();
  }

  private void clearFields() {
    ledgerCode.clear();
    ledgerName.clear();
    subledgerCode.clear();
    subledgerName.clear();
  }

  public void save(ActionEvent actionEvent) {
    Ledger ledger = null;
    Subledger subledger = null;
    try {
      ledger = createNewLedger();
      subledger = createNewSubledger();
    } catch (IllegalArgumentException illegalArgumentException) {
      masterValidationAlert.validationAlert(illegalArgumentException.getMessage());
    }

    ledgerServiceImpl.save(ledger);
    subledgerServiceImpl.save(subledger);
    clearFields();
    loadDetails();
  }

  private void loadDetails() {
    ledgerList.clear();
    subledgerList.clear();
    ledgerList.addAll(ledgerServiceImpl.findAll());
    subledgerList.addAll(subledgerServiceImpl.findAll());
    ledgerTable.setItems(ledgerList);
    subledgerTable.setItems(subledgerList);
    ledgerTable.setVisible(true);
    subledgerTable.setVisible(true);
  }

  public void delete(ActionEvent actionEvent) {
    Ledger selectedLedger = ledgerTable.getSelectionModel().getSelectedItem();
    Subledger selectedSubledger = subledgerTable.getSelectionModel().getSelectedItem();
    ledgerServiceImpl.delete(selectedLedger);
    subledgerServiceImpl.delete(selectedSubledger);
    loadDetails();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    subledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    ledgerTable.setEditable(false);
    subledgerTable.setEditable(false);
    setColumnProperties();
    loadDetails();
  }

  private void setColumnProperties() {
    colLedgerName.setCellValueFactory(new PropertyValueFactory<>("ledgerName"));
    colLedgerCode.setCellValueFactory(new PropertyValueFactory<>("ledgerCode"));
    colSubledgerName.setCellValueFactory(new PropertyValueFactory<>("subledgerName"));
    colSubledgerCode.setCellValueFactory(new PropertyValueFactory<>("subledgerCode"));
  }

}
