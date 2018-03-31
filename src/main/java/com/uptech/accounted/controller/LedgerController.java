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
import javafx.geometry.Rectangle2D;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;

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
    ledger.setLedgerCode(ledgerCode.getText());
    ledger.setLedgerName(ledgerName.getText());
    ledgerServiceImpl.save(ledger);
    loadLedgerDetails();
    return ledger;
  }

  public void resetLedger(ActionEvent actionEvent) {
    clearLedgerFields();
  }

  public void resetSubLedger(ActionEvent actionEvent) {
    clearSubLedgerFields();
  }

  private void clearLedgerFields() {
    ledgerCode.clear();
    ledgerName.clear();
  }

  private void clearSubLedgerFields() {
    subledgerCode.clear();
    subledgerName.clear();
  }

  public void saveLedger(ActionEvent actionEvent) {
    createNewLedger();
    loadLedgerDetails();
  }

  private void loadLedgerDetails() {
    ledgerList.clear();
    ledgerList.addAll(ledgerServiceImpl.findAll());
    ledgerTable.setItems(ledgerList);
    ledgerTable.setVisible(true);
  }

  public void deleteLedger(ActionEvent actionEvent) {
    Ledger selectedLedger = ledgerTable.getSelectionModel().getSelectedItem();
    if (selectedLedger != null)
      ledgerServiceImpl.delete(selectedLedger);
    loadLedgerDetails();
  }

  public void deleteSubledger(ActionEvent actionEvent) {
    Subledger selectedSubledger = subledgerTable.getSelectionModel().getSelectedItem();
    if (selectedSubledger != null)
      subledgerServiceImpl.delete(selectedSubledger);
    loadSubledgersForLedger();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    ledgerTable.setPrefHeight(visualBounds.getHeight()/2);
    subledgerTable.setPrefHeight(visualBounds.getHeight()/2);
    subledgerTable.getItems().clear();
    ledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    subledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    try {
      ledgerTable.setOnMouseClicked(event -> {
        loadSubledgersForLedger();
        Ledger selectedItem = ledgerTable.getSelectionModel().getSelectedItem();
        ledgerCode.setText(selectedItem.getLedgerCode());
        ledgerName.setText(selectedItem.getLedgerName());
      });
      subledgerTable.setOnMouseClicked(event -> {
        Subledger selectedItem = subledgerTable.getSelectionModel().getSelectedItem();
        subledgerCode.setText(selectedItem.getSubledgerId().getSubledgerCode());
        subledgerName.setText(selectedItem.getSubledgerName());
      });
    } catch(NullPointerException nullPointerException) {
      nullPointerException.getMessage();
    }

    ledgerTable.setEditable(false);
    subledgerTable.setEditable(false);
    setColumnProperties();
    loadLedgerDetails();
  }

  private void loadSubledgersForLedger() {
    Ledger selectedLedger = ledgerTable.getSelectionModel().getSelectedItem();
    subledgerTable.getItems().clear();
    subledgerTable.getItems().addAll(subledgerServiceImpl.findByLedgerCode(selectedLedger.getLedgerCode()));
    colSubledgerName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubledgerName()));
    colSubledgerCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubledgerId().getSubledgerCode()));
  }

  private void setColumnProperties() {
    colLedgerName.setCellValueFactory(new PropertyValueFactory<>("ledgerName"));
    colLedgerCode.setCellValueFactory(new PropertyValueFactory<>("ledgerCode"));
    colSubledgerName.setCellValueFactory(new PropertyValueFactory<>("subledgerName"));
    colSubledgerCode.setCellValueFactory(new PropertyValueFactory<>("subledgerCode"));
  }

  public void saveSubLedger(ActionEvent actionEvent) {
    try {
      Ledger ledgerByCode = ledgerServiceImpl.findByCode(ledgerCode.getText());

      if (ledgerByCode == null)
        throw new IllegalArgumentException("Ledger by code " + ledgerCode.getText() + " not found");
      Subledger subledger  = new Subledger(new SubledgerId(ledgerByCode.getLedgerCode(), subledgerCode.getText()),
                                subledgerName.getText());
      subledger.setLedger(ledgerByCode);
      subledgerServiceImpl.save(subledger);
      loadSubledgersForLedger();
    } catch (Exception exception) {
      masterValidationAlert.validationAlert(exception.getMessage());
    }
  }
}
