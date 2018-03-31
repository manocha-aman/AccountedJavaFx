package com.uptech.accounted.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.bean.LedgerType;
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
import javafx.scene.control.ComboBox;
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
  ComboBox<LedgerType> cbLedgerType;
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
  @FXML
  private TableColumn<Ledger, String> colLedgerType;
  @FXML
  private TableColumn<Subledger, String> colSubledgerCode;
  @FXML
  private TableColumn<Subledger, String> colSubledgerName;

  private ObservableList<Ledger> ledgerList = FXCollections.observableArrayList();

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
    ledger.setLedgerType(cbLedgerType.getValue());
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
    cbLedgerType.getSelectionModel().clearSelection();
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
    loadLedgerType();
    subledgerTable.getItems().clear();
    ledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    subledgerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    ledgerTable.setOnMouseClicked(event -> {
      loadSubledgersForLedger();
      Ledger selectedItem = ledgerTable.getSelectionModel().getSelectedItem();
      ledgerCode.setText(selectedItem.getLedgerCode());
      ledgerName.setText(selectedItem.getLedgerName());
      cbLedgerType.getSelectionModel().select(selectedItem.getLedgerType());
    });
    subledgerTable.setOnMouseClicked(event -> {
      Subledger selectedItem = subledgerTable.getSelectionModel().getSelectedItem();
      subledgerCode.setText(selectedItem.getSubledgerId().getSubledgerCode());
      subledgerName.setText(selectedItem.getSubledgerName());
    });

    ledgerTable.setEditable(false);
    subledgerTable.setEditable(false);
    setColumnProperties();
    loadLedgerDetails();
  }

  private void loadLedgerType() {
    System.out.println("loadLedgerType");
    List<LedgerType> ledgerTypes = new ArrayList<>();
    ledgerTypes = Arrays.asList(LedgerType.values());

    ObservableList<LedgerType> comboList = FXCollections.observableArrayList(ledgerTypes);
    cbLedgerType.setItems(comboList);
  }

  private void loadSubledgersForLedger() {
    Ledger selectedLedger = ledgerTable.getSelectionModel().getSelectedItem();
    subledgerTable.getItems().clear();
    subledgerTable.getItems().addAll(subledgerServiceImpl.findByLedgerCode(selectedLedger.getLedgerCode()));
    colSubledgerName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubledgerName()));
    colSubledgerCode
        .setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubledgerId().getSubledgerCode()));
  }

  private void setColumnProperties() {
    colLedgerName.setCellValueFactory(new PropertyValueFactory<>("ledgerName"));
    colLedgerCode.setCellValueFactory(new PropertyValueFactory<>("ledgerCode"));
    colLedgerType.setCellValueFactory(new PropertyValueFactory<>("ledgerType"));
    colSubledgerName.setCellValueFactory(new PropertyValueFactory<>("subledgerName"));
    colSubledgerCode.setCellValueFactory(new PropertyValueFactory<>("subledgerCode"));
  }

  public void saveSubLedger(ActionEvent actionEvent) {
    try {
      Ledger ledgerByCode = ledgerServiceImpl.findByCode(ledgerCode.getText());

      if (ledgerByCode == null)
        throw new IllegalArgumentException("Ledger by code " + ledgerCode.getText() + " not found");
      Subledger subledger = new Subledger(new SubledgerId(ledgerByCode.getLedgerCode(), subledgerCode.getText()),
          subledgerName.getText());
      subledger.setLedger(ledgerByCode);
      subledgerServiceImpl.save(subledger);
      loadSubledgersForLedger();
    } catch (Exception exception) {
      masterValidationAlert.validationAlert(exception.getMessage());
    }
  }
}
