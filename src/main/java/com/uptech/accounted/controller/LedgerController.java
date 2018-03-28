package com.uptech.accounted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.service.LedgerServiceImpl;
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
  TextField subLedgerName;
  @FXML
  TextField subLedgerCode;
  @FXML
  private TableView<Ledger> ledgerTable;
  @FXML
  private TableColumn<Ledger, Long> colLedgerId;
  @FXML
  private TableColumn<Ledger, String> colLedgerCode;
  @FXML
  private TableColumn<Ledger, String> colLedgerName;
  @FXML
  private TableColumn<Ledger, String> colSubLedgerCode;
  @FXML
  private TableColumn<Ledger, String> colSubLedgerName;
  private ObservableList<Ledger> ledgerList = FXCollections.observableArrayList();
  @Autowired
  private MasterValidationAlert masterValidationAlert;
  @Autowired
  public LedgerServiceImpl ledgerServiceImpl;

  public LedgerController(@Autowired LedgerServiceImpl service) {
  }

  private Ledger createNewLedger() {
    Ledger ledger = new Ledger();
    ledger.setLedgerCode(ledgerCode.getText());
    ledger.setLedgerName(ledgerName.getText());
    ledger.setSubLedgerCode(subLedgerCode.getText());
    ledger.setSubLedgerName(subLedgerName.getText());
    return ledger;
  }

  public void reset(ActionEvent actionEvent) {
    clearFields();
  }

  private void clearFields() {
    ledgerCode.clear();
    ledgerName.clear();
    subLedgerName.clear();
    subLedgerCode.clear();
  }

  public void save(ActionEvent actionEvent) {
    Ledger ledger = null;
    try {
      ledger = createNewLedger();
    } catch (IllegalArgumentException illegalArgumentException) {
      masterValidationAlert.validationAlert(illegalArgumentException.getMessage());
    }

    ledgerServiceImpl.save(ledger);
    clearFields();
    loadDetails();
  }

  private void loadDetails() {
    ledgerList.clear();
    ledgerList.addAll(ledgerServiceImpl.findAll());
    ledgerTable.setItems(ledgerList);
    ledgerTable.setVisible(true);
  }

  public void delete(ActionEvent actionEvent) {
    Ledger selectedItem = ledgerTable.getSelectionModel().getSelectedItem();
    ledgerServiceImpl.delete(selectedItem);
    loadDetails();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    ledgerTable.setEditable(false);
    setColumnProperties();
    loadDetails();
  }

  private void setColumnProperties() {
    colLedgerId.setCellValueFactory(new PropertyValueFactory<>("ledgerId"));
    colLedgerName.setCellValueFactory(new PropertyValueFactory<>("ledgerName"));
    colLedgerCode.setCellValueFactory(new PropertyValueFactory<>("ledgerCode"));
    colSubLedgerName.setCellValueFactory(new PropertyValueFactory<>("subLedgerName"));
    colSubLedgerCode.setCellValueFactory(new PropertyValueFactory<>("subLedgerCode"));
  }

}
