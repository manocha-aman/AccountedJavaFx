package com.uptech.accounted.controller;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahEra;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.uptech.accounted.bean.Department;
import com.uptech.accounted.bean.Initiator;
import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.bean.Master;
import com.uptech.accounted.bean.QTransaction;
import com.uptech.accounted.bean.Recipient;
import com.uptech.accounted.bean.Subledger;
import com.uptech.accounted.bean.SubledgerId;
import com.uptech.accounted.bean.Transaction;
import com.uptech.accounted.bean.TransactionType;
import com.uptech.accounted.config.StageManager;
import com.uptech.accounted.repository.DepartmentRepository;
import com.uptech.accounted.repository.InitiatorRepository;
import com.uptech.accounted.repository.RecipientRepository;
import com.uptech.accounted.repository.SubjectMatterRepository;
import com.uptech.accounted.repository.TransactionRepository;
import com.uptech.accounted.service.LedgerServiceImpl;
import com.uptech.accounted.service.SubjectMatterServiceImpl;
import com.uptech.accounted.service.SubledgerServiceImpl;
import com.uptech.accounted.service.TransactionServiceImpl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;

@Controller
public class ReportsController implements Initializable {

  @FXML
  private CheckComboBox<String> cbInitiators;

  @FXML
  private CheckComboBox<String> cbDepartments;

  @FXML
  private CheckComboBox<String> cbRecipients;

  @FXML
  private CheckComboBox<String> cbLedgerType;

  @FXML
  private CheckComboBox<String> cbSubledgerType;

  @FXML
  private CheckComboBox<String> cbSubjects;

  @FXML
  private CheckComboBox<TransactionType> cbTransactions;

  @FXML
  private DatePicker fromDate;

  @FXML
  private DatePicker toDate;

  @FXML
  private TextField tbFromAmount;

  @FXML
  private TextField tbToAmount;

  @FXML
  private Button reset;

  @FXML
  private Button generateReport;

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
  private TransactionRepository transactionRepository;

  @Autowired
  private RecipientRepository recipientRepository;

  @Autowired
  private SubjectMatterRepository subjectMatterRepository;

  @Autowired
  private TransactionServiceImpl transactionService;

  private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
  private ObservableList<String> ledgerComboList = FXCollections.observableArrayList();

  @FXML
  void reset(ActionEvent event) {
    clearFields();
  }

  private void clearFields() {
    cbInitiators.getCheckModel().clearChecks();
    cbDepartments.getCheckModel().clearChecks();
    fromDate.getEditor().clear();
    toDate.getEditor().clear();
    cbRecipients.getCheckModel().clearChecks();
    cbLedgerType.getCheckModel().clearChecks();
    cbSubledgerType.getCheckModel().clearChecks();
    cbSubjects.getCheckModel().clearChecks();
    cbTransactions.getCheckModel().clearChecks();
    tbFromAmount.clear();
    tbToAmount.clear();
  }

  @FXML
  private void generateReports(ActionEvent event) throws IOException {
    // JPAQuery query = new JPAQuery(entityManager);
    QTransaction transaction = QTransaction.transaction;
    List<Initiator> initiators = cbInitiators.getCheckModel().getCheckedItems().stream()
        .map(item -> new Initiator(item.split("-")[0], item.split("-")[1])).collect(toList());
    List<Department> departments = cbDepartments.getCheckModel().getCheckedItems().stream()
        .map(item -> new Department(item.split("-")[0], item.split("-")[1])).collect(toList());
    List<Recipient> recipients = cbRecipients.getCheckModel().getCheckedItems().stream()
        .map(item -> new Recipient(item.split("-")[0], item.split("-")[1])).collect(toList());
    List<TransactionType> transactionTypes = cbTransactions.getCheckModel().getCheckedItems().stream()
        .collect(toList());
    List<Subledger> subledgers = cbSubledgerType.getCheckModel().getCheckedItems().stream().filter(i -> i != null)
        .map(item -> {
          try {
            return new Subledger(new SubledgerId(item.split("-")[0], item.split("-")[2]), item.split("-")[3]);
          } catch (Exception e) {
            System.out.println(item);
            e.printStackTrace();
            return null;
          }
        }).collect(toList());

    BooleanExpression in = transaction.department.in(departments).and(transaction.initiator.in(initiators))
        .and(transaction.recipient.in(recipients)).and(transaction.transactionType.in(transactionTypes))
        .and(transaction.subledgerType.in(subledgers)).and(transaction.dateOfTransaction.after(fromDate.getValue()))
        .and(transaction.dateOfTransaction.before(toDate.getValue()));
    Iterable<Transaction> all = transactionRepository.findAll(in);

    save(event, all);
  }

  private void save(ActionEvent event, Iterable<Transaction> all) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    Node source = (Node) event.getSource();
    Window stage = source.getScene().getWindow();
    File file = fileChooser.showSaveDialog(stage);

    if (file != null) {
      try (FileWriter fileWriter = new FileWriter(file)) {
        fileWriter.write(
            "Id,Initiator,Department,Date,Recipient,Ledger,Sub Ledger,Amount,Subject,Transaction Type,Narration");
        fileWriter.write("\n");
        for (Transaction transaction : all) {
          fileWriter.write("" + transaction);
          fileWriter.write("\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadTransactionTypes();
    loadInitiators();
    loadDepartments();
    loadLedgers();
    loadRecipients();
    loadSubjectMatters();
    makeAmountFieldNumericOnly();
    setupDefaults();

    fromDate.setConverter(new StringConverter<LocalDate>() {
      String pattern = "dd-MM-yyyy";
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

      {
        fromDate.setPromptText(pattern.toLowerCase());
      }

      @Override
      public String toString(LocalDate date) {
        if (date != null) {
          return dateFormatter.format(date);
        } else {
          return "";
        }
      }

      @Override
      public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
          return LocalDate.parse(string, dateFormatter);
        } else {
          return null;
        }
      }
    });

    toDate.setConverter(new StringConverter<LocalDate>() {
      String pattern = "dd-MM-yyyy";
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

      {
        toDate.setPromptText(pattern.toLowerCase());
      }

      @Override
      public String toString(LocalDate date) {
        if (date != null) {
          return dateFormatter.format(date);
        } else {
          return "";
        }
      }

      @Override
      public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
          return LocalDate.parse(string, dateFormatter);
        } else {
          return null;
        }
      }
    });

  }

  private void setupDefaults() {
    fromDate.setValue(LocalDate.now(ZoneId.of("Asia/Kolkata")).minusDays(10));
    toDate.setValue(LocalDate.now(ZoneId.of("Asia/Kolkata")));
    
    HijrahChronology hijriChronology = HijrahChronology.INSTANCE;
    fromDate.setChronology(hijriChronology);
    toDate.setChronology(hijriChronology);
    
    cbInitiators.getCheckModel().checkAll();
    cbDepartments.getCheckModel().checkAll();
    cbRecipients.getCheckModel().checkAll();
    cbTransactions.getCheckModel().checkAll();
    cbSubjects.getCheckModel().checkAll();
    cbLedgerType.getCheckModel().checkAll();
    cbSubledgerType.getCheckModel().checkAll();
    tbFromAmount.setText("0.0");
    tbToAmount.setText("99999999.0");
  }

  private void makeAmountFieldNumericOnly() {
    // force the field to be numeric only
    makeAmountFieldsNumeric(this.tbFromAmount);
    makeAmountFieldsNumeric(this.tbToAmount);
  }

  private void makeAmountFieldsNumeric(TextField amountField) {
    amountField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d{0,10}([\\.]\\d{0,2})?")) {
          amountField.setText(oldValue);
        }
      }
    });
  }

  private void loadInitiators() {
    loadMaster(initiatorRepository, cbInitiators);
  }

  private <T extends Master> void loadMaster(JpaRepository<T, String> repository, CheckComboBox<String> comboBox) {
    List<T> list = repository.findAll();
    ObservableList<String> comboList = FXCollections.observableArrayList();
    for (T item : list) {
      comboList.add(item.getCode() + "-" + item.getName());
    }
    comboBox.getItems().addAll(comboList);
  }

  private void loadDepartments() {
    loadMaster(departmentRepository, cbDepartments);
  }

  private void loadTransactionTypes() {
    List<TransactionType> transactionTypes = Arrays.asList(TransactionType.values());

    ObservableList<TransactionType> comboList = FXCollections.observableArrayList(transactionTypes);
    cbTransactions.getItems().addAll(comboList);
  }

  private void loadSubjectMatters() {
    loadMaster(subjectMatterRepository, cbSubjects);
  }

  private void loadRecipients() {
    loadMaster(recipientRepository, cbRecipients);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void loadLedgers() {
    ledgerComboList.clear();
    List<Ledger> ledgerList = ledgerServiceImpl.findAll();
    for (Ledger ledger : ledgerList) {
      ledgerComboList.add(ledger.getLedgerCode() + "-" + ledger.getLedgerName());
    }
    cbLedgerType.getItems().clear();
    cbLedgerType.getItems().addAll(ledgerComboList);
    cbLedgerType.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
      public void onChanged(ListChangeListener.Change<? extends String> c) {
        loadSubledgers();
      }
    });
  }

  @FXML
  public void loadSubledgers() {
    ObservableList<String> subledgerComboList = FXCollections.observableArrayList();
    List<String> ledgers = cbLedgerType.getCheckModel().getCheckedItems().stream().map(l -> l.split("-")[0])
        .collect(toList());
    for (String ledger : ledgers) {
      if (ledger.trim().isEmpty())
        continue;
      List<Subledger> subledgers = subledgerServiceImpl.findByLedgerCode(ledger);
      for (Subledger subledger : subledgers) {
        subledgerComboList.add(subledger.getLedger().getLedgerCode() + "-" + subledger.getLedger().getLedgerName() + "-"
            + subledger.getSubledgerId().getSubledgerCode() + "-" + subledger.getSubledgerName());
      }
    }
    cbSubledgerType.getItems().clear();
    cbSubledgerType.getItems().addAll(subledgerComboList);
    cbSubledgerType.getCheckModel().checkAll();
  }

  public void checkAllInitiators(ActionEvent actionEvent) {
    cbInitiators.getCheckModel().checkAll();
  }

  public void uncheckAllInitiators(ActionEvent actionEvent) {
    cbInitiators.getCheckModel().clearChecks();
  }

  public void checkAllDepartments(ActionEvent actionEvent) {
    cbDepartments.getCheckModel().checkAll();
  }

  public void uncheckAllDepartments(ActionEvent actionEvent) {
    cbDepartments.getCheckModel().clearChecks();
  }

  public void checkAllRecipients(ActionEvent actionEvent) {
    cbRecipients.getCheckModel().checkAll();
  }

  public void uncheckAllRecipients(ActionEvent actionEvent) {
    cbRecipients.getCheckModel().clearChecks();
  }

  public void checkAllLedgerType(ActionEvent actionEvent) {
    cbLedgerType.getCheckModel().checkAll();
  }

  public void uncheckAllLedgerType(ActionEvent actionEvent) {
    cbLedgerType.getCheckModel().clearChecks();
  }

  public void checkAllSubLedgerType(ActionEvent actionEvent) {
    cbSubledgerType.getCheckModel().checkAll();
  }

  public void uncheckAllSubLedgerType(ActionEvent actionEvent) {
    cbSubledgerType.getCheckModel().clearChecks();
  }

  public void checkAllSubjects(ActionEvent actionEvent) {
    cbSubjects.getCheckModel().checkAll();
  }

  public void uncheckAllSubjects(ActionEvent actionEvent) {
    cbSubjects.getCheckModel().clearChecks();
  }
}
