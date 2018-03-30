package com.uptech.accounted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.bean.Subledger;
import com.uptech.accounted.bean.SubledgerId;
import com.uptech.accounted.service.LedgerServiceImpl;
import com.uptech.accounted.service.SubledgerServiceImpl;
import com.uptech.accounted.validations.MasterValidationAlert;

import javafx.beans.property.SimpleStringProperty;
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
  private ObservableList<Subledger> subledgerList = FXCollections.observableArrayList();
  @FXML
  private TableColumn<Subledger, String> colLedgerCodeReference;
  @FXML
  private TableColumn<Subledger, String> colSubledgerCode;
  @FXML
  private TableColumn<Subledger, String> colSubledgerName;
  @Autowired
  private MasterValidationAlert masterValidationAlert;
  @Autowired
  public LedgerServiceImpl ledgerServiceImpl;
  @Autowired
  public SubledgerServiceImpl subledgerServiceImpl;

  public LedgerController(@Autowired LedgerServiceImpl service) {
  }

  private Ledger createNewLedger() {
    Ledger ledger = new Ledger();
    ledger.setLedgerCode(Long.parseLong(ledgerCode.getText()));
    ledger.setLedgerName(ledgerName.getText());
    ledgerServiceImpl.save(ledger);
    loadDetails();
    return ledger;
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
    Ledger ledgerByCode = ledgerServiceImpl.findByCode(Long.parseLong(ledgerCode.getText()));
    Subledger subledger = null;
    try {
      if(ledgerByCode==null)
        ledgerByCode = createNewLedger();
      subledger = new Subledger(new SubledgerId(ledgerByCode.getLedgerCode(), Long.parseLong(subledgerCode.getText())), subledgerName.getText());
      subledger.setLedger(ledgerByCode);
    } catch (IllegalArgumentException illegalArgumentException) {
      masterValidationAlert.validationAlert(illegalArgumentException.getMessage());
    }

    subledgerServiceImpl.save(subledger);
    clearFields();
    loadDetails();
  }

  private void loadDetails() {
    ledgerList.clear();
    ledgerList.addAll(ledgerServiceImpl.findAll());
    ledgerTable.setItems(ledgerList);
    ledgerTable.setVisible(true);
    subledgerList.clear();
    subledgerList.addAll(subledgerServiceImpl.findAll());
    subledgerTable.setItems(subledgerList);
    subledgerTable.setVisible(true);
  }

  public void delete(ActionEvent actionEvent) {
    Ledger selectedLedger = ledgerTable.getSelectionModel().getSelectedItem();
    Subledger selectedSubledger = subledgerTable.getSelectionModel().getSelectedItem();
    if (selectedLedger != null) {
      ledgerServiceImpl.delete(selectedLedger);
    } else if (selectedSubledger != null) {
      subledgerServiceImpl.delete(selectedSubledger);
    }
    loadDetails();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    subledgerTable.getItems().clear();
    ledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    subledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    ledgerTable.setOnMouseClicked(event -> {
      Ledger selectedLedger = ledgerTable.getSelectionModel().getSelectedItem();
      subledgerTable.getItems().clear();
      subledgerTable.getItems().addAll(subledgerServiceImpl.findByLedgerCode(selectedLedger.getLedgerCode()));
      colSubledgerName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubledgerName()));
      colSubledgerCode
          .setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubledgerId().toString()));
    });

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
