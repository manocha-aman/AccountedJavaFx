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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

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

	Callback<TableColumn<Transaction, Boolean>, TableCell<Transaction, Boolean>> cellFactory = new Callback<TableColumn<Transaction, Boolean>, TableCell<Transaction, Boolean>>() {
		@Override
		public TableCell<Transaction, Boolean> call(final TableColumn<Transaction, Boolean> param) {
			final TableCell<Transaction, Boolean> cell = new TableCell<Transaction, Boolean>() {
				final Button btnEdit = new Button();
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Transaction transaction = getTableView().getItems().get(getIndex());
							updateTransaction(transaction);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateTransaction(Transaction transaction) {
					cbInitiator.getSelectionModel().select(transaction.getInitiator().getName());
					cbDepartment.getSelectionModel().select(transaction.getDepartment().getName());
					cbRecipient.getSelectionModel().select(transaction.getRecipient().getName());
					cbLedgerType.getSelectionModel().select(transaction.getLedgerType().getLedgerName());
					dateOfTransaction.setValue(transaction.getDateOfTransaction());
					amount.setText(transaction.getAmount().toString());
					subjectMatter.setText(transaction.getSubjectMatter());
				}
			};
			return cell;
		}
	};

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
		alert.setContentText("New entry created by " + transaction.getInitiator() + ". Id - " + transaction.getTransactionId());
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
		departmentList.forEach(System.out::print);
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
		colEdit.setCellFactory(cellFactory);
	}
}
