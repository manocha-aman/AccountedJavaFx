package com.uptech.accounted.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.bean.Master;
import com.uptech.accounted.bean.Subledger;
import com.uptech.accounted.bean.Transaction;
import com.uptech.accounted.config.StageManager;
import com.uptech.accounted.repository.DepartmentRepository;
import com.uptech.accounted.repository.InitiatorRepository;
import com.uptech.accounted.repository.RecipientRepository;
import com.uptech.accounted.repository.SubjectMatterRepository;
import com.uptech.accounted.service.LedgerServiceImpl;
import com.uptech.accounted.service.SubjectMatterServiceImpl;
import com.uptech.accounted.service.SubledgerServiceImpl;
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
import javafx.scene.input.KeyEvent;

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
  private ComboBox<String> cbSubledgerType;

  @FXML
  private ComboBox<String> cbSubjectMatter;

  @FXML
  private DatePicker dateOfTransaction;

  @FXML
  private TextField amount;

  @FXML
  private TextField narration;

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
  private TableColumn<Transaction, String> colSubledgerType;

  @FXML
  private TableColumn<Transaction, BigDecimal> colAmount;

  @FXML
  private TableColumn<Transaction, String> colNarration;

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
  private SubjectMatterServiceImpl subjectMatterServiceImpl;

  @Autowired
  private LedgerServiceImpl ledgerServiceImpl;

  @Autowired
  private SubledgerServiceImpl subledgerServiceImpl;

  @Autowired
  private RecipientRepository recipientRepository;

  @Autowired
  private SubjectMatterRepository subjectMatterRepository;

  @Autowired
  private TransactionServiceImpl transactionService;

  private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
  private ObservableList<String> subledgerComboList = FXCollections.observableArrayList();
  private ObservableList<String> ledgerComboList = FXCollections.observableArrayList();

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
    cbSubledgerType.getSelectionModel().clearSelection();
    cbSubjectMatter.getSelectionModel().clearSelection();
    amount.clear();
    narration.clear();
  }

  @FXML
  private void saveTransaction(ActionEvent event) {
    Transaction transaction = new Transaction();
    transaction.setInitiator(initiatorRepository.findOne(getInitiator()));
    transaction.setDepartment(departmentRepository.findOne(getDepartment()));
    transaction.setDateOfTransaction(getDateOfTransaction());
    transaction.setRecipient(recipientRepository.findOne(getRecipient()));
    transaction.setSubledgerType(subledgerServiceImpl.findByLedgerAndSubledgerCode(Long.parseLong(getLedgerCode()),
        Long.parseLong(getSubledgerCode())));
    transaction.setLedgerType(ledgerServiceImpl.findByCode(Long.parseLong(getLedgerCode())));
    transaction.setAmount(new BigDecimal(getAmount()));
    transaction.setNarration(getNarration());
    transaction.setSubjectMatter(subjectMatterServiceImpl.findByCode(getSubjectMatter()));

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

  public String getSubjectMatter() {
    return cbSubjectMatter.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public String getRecipient() {
    return cbRecipient.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public String getLedgerCode() {
    return cbLedgerType.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public String getSubledgerCode() {
    return cbSubledgerType.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public LocalDate getDateOfTransaction() {
    return dateOfTransaction.getValue();
  }

  public String getAmount() {
    return amount.getText();
  }

  public String getNarration() {
    return amount.getText();
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
    loadSubjectMatters();
    transactionTable.setOnMouseClicked(event -> {
      try {
        Transaction selectedItem = transactionTable.getSelectionModel().getSelectedItem();
        cbInitiator.getSelectionModel()
            .select(selectedItem.getInitiator().getCode() + "-" + selectedItem.getInitiator().getName());
        cbDepartment.getSelectionModel()
            .select(selectedItem.getDepartment().getCode() + "-" + selectedItem.getDepartment().getName());
        cbRecipient.getSelectionModel()
            .select(selectedItem.getRecipient().getCode() + "-" + selectedItem.getRecipient().getName());
        cbLedgerType.getSelectionModel()
            .select(selectedItem.getLedgerType().getLedgerCode() + "-" + selectedItem.getLedgerType().getLedgerName());
        cbSubledgerType.getSelectionModel().select(selectedItem.getSubledgerType().getSubledgerId().getSubledgerCode()
            + "-" + selectedItem.getSubledgerType().getSubledgerName());
        cbSubjectMatter.getSelectionModel().select(selectedItem.getSubjectMatter().getCode() + "-" + selectedItem.getSubjectMatter().getName());
        dateOfTransaction.setValue(selectedItem.getDateOfTransaction());
        amount.setText(selectedItem.getAmount().toString());
        narration.setText(selectedItem.getNarration().toString());
      } catch (NullPointerException nullPointerException) {
        nullPointerException.getMessage();
      }
    });
    transactionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    setColumnProperties();
    // Add all transactions into table
    loadTransactionDetails();
  }

  private void loadInitiators() {
    loadMaster(initiatorRepository, cbInitiator);
  }

  private void loadDepartments() {
    loadMaster(departmentRepository, cbDepartment);
  }

  private void loadSubjectMatters() {
    loadMaster(subjectMatterRepository, cbSubjectMatter);
  }

  private void loadRecipients() {
    loadMaster(recipientRepository, cbRecipient);
  }

  private <T extends Master> void loadMaster(JpaRepository<T, String> repository, ComboBox<String> comboBox) {
    List<T> list = repository.findAll();
    ObservableList<String> comboList = FXCollections.observableArrayList();
    for (T item : list) {
      comboList.add(item.getCode() + "-" + item.getName());
    }
    comboBox.setItems(comboList);
    comboBox.addEventHandler(KeyEvent.KEY_PRESSED, new AutoCompleteComboBoxListener<T>(comboBox, list));
  }

  private void loadLedgers() {
    ledgerComboList.clear();
    List<Ledger> ledgerList = ledgerServiceImpl.findAll();
    for (Ledger ledger : ledgerList) {
      ledgerComboList.add(ledger.getLedgerCode() + "-" + ledger.getLedgerName());
    }
    cbLedgerType.setItems(ledgerComboList);
  }

  @FXML
  public void loadSubledgers(ActionEvent event) {
    subledgerComboList.clear();
    List<Subledger> subledgerList = subledgerServiceImpl.findByLedgerCode(Long.parseLong(getLedgerCode()));
    for (Subledger subledger : subledgerList) {
      subledgerComboList.add(subledger.getSubledgerId().getSubledgerCode() + "-" + subledger.getSubledgerName());
    }
    cbSubledgerType.setItems(subledgerComboList);
  }

  private void setColumnProperties() {
    colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
    colInitiator.setCellValueFactory(new PropertyValueFactory<>("initiator"));
    colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
    colDateOfTransaction.setCellValueFactory(new PropertyValueFactory<>("dateOfTransaction"));
    colLedgerType.setCellValueFactory(new PropertyValueFactory<>("ledgerType"));
    colRecipient.setCellValueFactory(new PropertyValueFactory<>("recipient"));
    colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    colNarration.setCellValueFactory(new PropertyValueFactory<>("narration"));
    colSubjectMatter.setCellValueFactory(new PropertyValueFactory<>("subjectMatter"));
    colSubledgerType.setCellValueFactory(new PropertyValueFactory<>("subledgerType"));
  }

}
