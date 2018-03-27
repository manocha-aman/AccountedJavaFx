package com.uptech.accounted.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Department;
import com.uptech.accounted.bean.Initiator;
import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.bean.Recipient;
import com.uptech.accounted.bean.Transaction;
import com.uptech.accounted.config.StageManager;
import com.uptech.accounted.repository.DepartmentRepository;
import com.uptech.accounted.repository.InitiatorRepository;
import com.uptech.accounted.repository.LedgerRepository;
import com.uptech.accounted.repository.RecipientRepository;
import com.uptech.accounted.service.TransactionServiceImpl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class TransactionController implements Initializable {

  @FXML
  private ComboBox<String> cbInitiator;

  @FXML
  private ComboBox<String> cbDepartment;

  @FXML
  private ComboBox<String> cbRecipient;

  @FXML
  private ComboBox<String> cbLedgerType;

  @FXML
  private DatePicker dateOfTransaction;

  @FXML
  private TextField amount;

  @FXML
  private TextField subjectMatter;

  @FXML
  private Button reset;

  @FXML
  private Button saveTransaction;

  @FXML
  private TableView<Transaction> transactionTable;

  @FXML
  private TableColumn<Transaction, Long> colTransactionId;

  @FXML
  private TableColumn<Transaction, String> colInitiator;

  @FXML
  private TableColumn<Transaction, String> colDepartment;

  @FXML
  private TableColumn<Transaction, LocalDate> colDateOfTransaction;

  @FXML
  private TableColumn<Transaction, String> colRecipient;

  @FXML
  private TableColumn<Transaction, String> colLedgerType;

  @FXML
  private TableColumn<Transaction, BigDecimal> colAmount;

  @FXML
  private TableColumn<Transaction, String> colSubjectMatter;

  @FXML
  private TableColumn<Transaction, Boolean> colEdit;

  @FXML
  private Button deleteTransactions;

  @Lazy
  @Autowired
  private StageManager stageManager;

  @Autowired
  private InitiatorRepository initiatorRepository;

  @Autowired
  private DepartmentRepository departmentRepository;

  @Autowired
  private LedgerRepository ledgerRepository;

  @Autowired
  private RecipientRepository recipientRepository;

  @Autowired
  private TransactionServiceImpl transactionService;

  private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
  private ObservableList<String> intiatorComboList = FXCollections.observableArrayList();
  private ObservableList<String> departmentComboList = FXCollections.observableArrayList();
  private ObservableList<String> ledgerComboList = FXCollections.observableArrayList();
  private ObservableList<String> recipientComboList = FXCollections.observableArrayList();

  @FXML
  private void exit(ActionEvent event) {
    Platform.exit();
  }

  @FXML
  void reset(ActionEvent event) {
    clearFields();
  }

  private void clearFields() {
    cbInitiator.getSelectionModel().clearSelection();
    cbDepartment.getSelectionModel().clearSelection();
    dateOfTransaction.getEditor().clear();
    cbRecipient.getSelectionModel().clearSelection();
    cbLedgerType.getSelectionModel().clearSelection();
    amount.clear();
    subjectMatter.clear();
  }

  @FXML
  private void saveTransaction(ActionEvent event) {
    Transaction transaction = new Transaction();
    transaction.setInitiator(initiatorRepository.findOne(getInitiator()));
    transaction.setDepartment(departmentRepository.findOne(getDepartment()));
    transaction.setDateOfTransaction(getDateOfTransaction());
    transaction.setRecipient(recipientRepository.findOne(getRecipient()));
    transaction.setLedgerType(ledgerRepository.findOne(getLedgerId()));
    transaction.setAmount(new BigDecimal(getAmount()));
    transaction.setSubjectMatter(getSubjectMatter());

    Transaction newTransaction = transactionService.save(transaction);
    loadTransactionDetails();

    saveAlert(newTransaction);
  }

  @FXML
  private void deleteTransactions(ActionEvent event) {
    Transaction transaction = transactionTable.getSelectionModel().getSelectedItem();
    transactionService.delete(transaction);
    loadTransactionDetails();
  }

  private void saveAlert(Transaction transaction) {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Transaction saved successfully.");
    alert.setHeaderText(null);
    alert.setContentText(
        "New entry created by " + transaction.getInitiator() + ". Id - " + transaction.getTransactionId());
    alert.showAndWait();
  }

  public String getInitiator() {
    return cbInitiator.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public String getDepartment() {
    return cbDepartment.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public String getRecipient() {
    return cbRecipient.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public long getLedgerId() {
    return Long.parseLong(cbLedgerType.getSelectionModel().getSelectedItem().split("-")[0]);
  }

  public LocalDate getDateOfTransaction() {
    return dateOfTransaction.getValue();
  }

  public String getAmount() {
    return amount.getText();
  }

  public String getSubjectMatter() {
    return subjectMatter.getText();
  }

  private void loadTransactionDetails() {
    transactionList.clear();
    transactionList.addAll(transactionService.findAll());

    transactionTable.setItems(transactionList);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    loadInitiators();
    loadDepartments();
    loadLedgers();
    loadRecipients();
    transactionTable.setOnMouseClicked(event -> {
      Transaction selectedItem = transactionTable.getSelectionModel().getSelectedItem();
      cbInitiator.getSelectionModel().select(selectedItem.getInitiator().getCode() + "-" + selectedItem.getInitiator().getName());
      cbDepartment.getSelectionModel().select(selectedItem.getDepartment().getCode() + "-" + selectedItem.getDepartment().getName());
      cbRecipient.getSelectionModel().select(selectedItem.getRecipient().getCode() + "-" + selectedItem.getRecipient().getName());
      cbLedgerType.getSelectionModel().select(selectedItem.getLedgerType().getLedgerCode() + "-" + selectedItem.getLedgerType().getLedgerName());
      dateOfTransaction.setValue(selectedItem.getDateOfTransaction());
      amount.setText(selectedItem.getAmount().toString());
      subjectMatter.setText(selectedItem.getSubjectMatter());
    });
    transactionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    setColumnProperties();
    // Add all transactions into table
    loadTransactionDetails();
  }

  private void loadInitiators() {
    List<Initiator> initiatorList = initiatorRepository.findAll();
    for (Initiator initiator : initiatorList) {
      intiatorComboList.add(initiator.getCode() + "-" + initiator.getName());
    }
    cbInitiator.setItems(intiatorComboList);
  }

  private void loadDepartments() {
    List<Department> departmentList = departmentRepository.findAll();
    for (Department department : departmentList) {
      departmentComboList.add(department.getCode() + "-" + department.getName());
    }
    cbDepartment.setItems(departmentComboList);
  }

  private void loadLedgers() {
    List<Ledger> ledgerList = ledgerRepository.findAll();
    for (Ledger ledger : ledgerList) {
      ledgerComboList.add(ledger.getLedgerCode() + "-" + ledger.getLedgerName());
    }
    cbLedgerType.setItems(ledgerComboList);
  }

  private void loadRecipients() {
    List<Recipient> recipientList = recipientRepository.findAll();
    for (Recipient recipient : recipientList) {
      recipientComboList.add(recipient.getCode() + "-" + recipient.getName());
    }
    cbRecipient.setItems(recipientComboList);
  }

  private void setColumnProperties() {
    colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
    colInitiator.setCellValueFactory(new PropertyValueFactory<>("initiator"));
    colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
    colDateOfTransaction.setCellValueFactory(new PropertyValueFactory<>("dateOfTransaction"));
    colRecipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
    colLedgerType.setCellValueFactory(new PropertyValueFactory<>("ledgerType"));
    colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    colSubjectMatter.setCellValueFactory(new PropertyValueFactory<>("subjectMatter"));
  }
}
