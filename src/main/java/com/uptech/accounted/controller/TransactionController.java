package com.uptech.accounted.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.uptech.accounted.bean.TransactionType;
import com.uptech.accounted.config.StageManager;
import com.uptech.accounted.repository.DepartmentRepository;
import com.uptech.accounted.repository.InitiatorRepository;
import com.uptech.accounted.repository.RecipientRepository;
import com.uptech.accounted.repository.SubjectMatterRepository;
import com.uptech.accounted.service.LedgerServiceImpl;
import com.uptech.accounted.service.SubjectMatterServiceImpl;
import com.uptech.accounted.service.SubledgerServiceImpl;
import com.uptech.accounted.service.TransactionServiceImpl;
import com.uptech.accounted.validations.MasterValidationAlert;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextArea;
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
  private ComboBox<TransactionType> cbTransactionType;

  @FXML
  private DatePicker dateOfTransaction;

  @FXML
  private TextField amount;

  @FXML
  private TextArea narration;

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
  private TableColumn<Transaction, String> colTransactionType;

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
  private TransactionServiceImpl transactionServiceImpl;

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
  TextField id;

  @FXML
  private void exit(ActionEvent event) {
    Platform.exit();
  }

  @FXML
  void reset(ActionEvent event) {
    clearFields();
  }

  private void clearFields() {
    id.clear();
    cbInitiator.getSelectionModel().clearSelection();
    cbDepartment.getSelectionModel().clearSelection();
    dateOfTransaction.getEditor().clear();
    cbRecipient.getSelectionModel().clearSelection();
    cbLedgerType.getSelectionModel().clearSelection();
    cbSubledgerType.getSelectionModel().clearSelection();
    cbSubjectMatter.getSelectionModel().clearSelection();
    cbTransactionType.getSelectionModel().clearSelection();
    amount.clear();
    narration.clear();
    saveTransaction.setText("Save");
  }

  @FXML
  private void saveTransaction(ActionEvent event) {
    Transaction transaction = new Transaction();
    if (getId() > 0)
      transaction = transactionServiceImpl.findById(getId());
    transaction.setInitiator(initiatorRepository.findOne(getInitiator()));
    transaction.setDepartment(departmentRepository.findOne(getDepartment()));
    transaction.setDateOfTransaction(getDateOfTransaction());
    transaction.setRecipient(recipientRepository.findOne(getRecipient()));
    transaction
        .setSubledgerType(subledgerServiceImpl.findByLedgerAndSubledgerCode((getLedgerCode()), getSubledgerCode()));
    transaction.setLedgerType(ledgerServiceImpl.findByCode(getLedgerCode()));
    BigDecimal transactionAmount = new BigDecimal(getAmount());
    transaction.setAmount(transactionAmount);
    transaction.setNarration(getNarration());
    transaction.setTransactionType(getTransactionType());
    transaction.setSubjectMatter(subjectMatterServiceImpl.findByCode(getSubjectMatter()));

    Transaction newTransaction = transactionService.save(transaction);
    loadTransactionDetails();
    saveTransaction.setText("Save");
    saveAlert(newTransaction);
    clearFields();
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
        "New entry created by " + transaction.getInitiator().getName() + ". Id - " + transaction.getTransactionId());
    alert.showAndWait();
  }

  public String getInitiator() {
    return cbInitiator.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public String getDepartment() {
    return cbDepartment.getSelectionModel().getSelectedItem().split("-")[0];
  }

  public TransactionType getTransactionType() {
    return cbTransactionType.getSelectionModel().getSelectedItem();
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
    return narration.getText();
  }

  public long getId() {
    try {
      return Long.parseLong(id.getText());
    } catch (Exception exception) {
      // Do nothing. This will be a new entry case
    }
    return 0;
  }

  private void loadTransactionDetails() {
    transactionList.clear();
    transactionList.addAll(transactionService.findAll());

    transactionTable.setItems(transactionList);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    id.setDisable(true);
    loadTransactionTypes();
    loadInitiators();
    loadDepartments();
    loadLedgers();
    loadRecipients();
    loadSubjectMatters();
    makeAmountFieldNumericOnly();

    cbSubledgerType.addEventHandler(KeyEvent.KEY_PRESSED, new AutoCompleteComboBoxListener(cbSubledgerType));

    transactionTable.setOnMouseClicked(event -> {
      try {
        Transaction selectedItem = transactionTable.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(selectedItem.getTransactionId()));
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
        cbTransactionType.getSelectionModel().select(selectedItem.getTransactionType());
        cbSubjectMatter.getSelectionModel()
            .select(selectedItem.getSubjectMatter().getCode() + "-" + selectedItem.getSubjectMatter().getName());
        dateOfTransaction.setValue(selectedItem.getDateOfTransaction());
        amount.setText(selectedItem.getAmount().toString());
        narration.setText(selectedItem.getNarration().toString());
        saveTransaction.setText("Update");
      } catch (Exception exception) {
        //No rows selected in the table
      }
    });
    transactionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    setColumnProperties();
    // Add all transactions into table
    loadTransactionDetails();
  }

  private void makeAmountFieldNumericOnly() {
    // force the field to be numeric only
    amount.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
          amount.setText(oldValue);
        }
      }
    });
  }

  private void loadInitiators() {
    loadMaster(initiatorRepository, cbInitiator);
  }

  private void loadDepartments() {
    loadMaster(departmentRepository, cbDepartment);
  }

  private void loadTransactionTypes() {
    List<TransactionType> transactionTypes = new ArrayList<>();
    transactionTypes = Arrays.asList(TransactionType.values());

    ObservableList<TransactionType> comboList = FXCollections.observableArrayList(transactionTypes);
    cbTransactionType.setItems(comboList);
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
    comboBox.addEventHandler(KeyEvent.KEY_PRESSED, new AutoCompleteComboBoxListener<T>(comboBox));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void loadLedgers() {
    ledgerComboList.clear();
    List<Ledger> ledgerList = ledgerServiceImpl.findAll();
    for (Ledger ledger : ledgerList) {
      ledgerComboList.add(ledger.getLedgerCode() + "-" + ledger.getLedgerName());
    }
    cbLedgerType.setItems(ledgerComboList);
    cbLedgerType.addEventHandler(KeyEvent.KEY_PRESSED, new AutoCompleteComboBoxListener(cbLedgerType));
  }

  @FXML
  public void loadSubledgers(ActionEvent event) {
    subledgerComboList.clear();
    List<Subledger> subledgerList = subledgerServiceImpl.findByLedgerCode(getLedgerCode());
    for (Subledger subledger : subledgerList) {
      subledgerComboList.add(subledger.getSubledgerId().getSubledgerCode() + "-" + subledger.getSubledgerName());
    }
    cbSubledgerType.setItems(subledgerComboList);
    cbSubledgerType.addEventHandler(KeyEvent.KEY_PRESSED, new AutoCompleteComboBoxListener(cbSubledgerType));
  }

  private void setColumnProperties() {
    colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
    colInitiator.setCellValueFactory(new PropertyValueFactory<>("initiatorName"));
    colDepartment.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
    colDateOfTransaction.setCellValueFactory(new PropertyValueFactory<>("dateOfTransaction"));
    colLedgerType.setCellValueFactory(new PropertyValueFactory<>("ledgerName"));
    colRecipient.setCellValueFactory(new PropertyValueFactory<>("recipientName"));
    colTransactionType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
    colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    colNarration.setCellValueFactory(new PropertyValueFactory<>("narration"));
    colSubjectMatter.setCellValueFactory(new PropertyValueFactory<>("subjectMatterName"));
    colSubledgerType.setCellValueFactory(new PropertyValueFactory<>("subledgerName"));
  }

}
