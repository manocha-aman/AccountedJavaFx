package com.accounted.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.accounted.bean.Department;
import com.accounted.bean.Initiator;
import com.accounted.bean.Ledger;
import com.accounted.bean.Receiver;
import com.accounted.bean.Transaction;
import com.accounted.config.StageManager;
import com.accounted.repository.DepartmentRepository;
import com.accounted.repository.InitiatorRepository;
import com.accounted.repository.LedgerRepository;
import com.accounted.repository.ReceiverRepository;
import com.accounted.service.TransactionServiceImpl;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class TransactionController implements Initializable {

	@FXML
	private ComboBox<String> cbInitiator;

	@FXML
	private ComboBox<String> cbDepartment;

	@FXML
	private ComboBox<String> cbReceiver;

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
					cbInitiator.getSelectionModel().select(transaction.getInitiator());
					cbDepartment.getSelectionModel().select(transaction.getDepartment());
					cbReceiver.getSelectionModel().select(transaction.getReceiver());
					cbLedgerType.getSelectionModel().select(transaction.getLedgerType());
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
	private TableColumn<Transaction, String> colReceiver;

	@FXML
	private TableColumn<Transaction, String> colLedgerType;

	@FXML
	private TableColumn<Transaction, BigDecimal> colAmount;

	@FXML
	private TableColumn<Transaction, String> colSubjectMatter;

	@FXML
	private TableColumn<Transaction, Boolean> colEdit;

	@FXML
	private MenuItem deleteTransactions;

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
	private ReceiverRepository receiverRepository;
	
	@Autowired
	private TransactionServiceImpl transactionService;

	private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
	private ObservableList<String> intiatorComboList = FXCollections.observableArrayList();
	private ObservableList<String> departmentComboList = FXCollections.observableArrayList();
	private ObservableList<String> ledgerComboList = FXCollections.observableArrayList();
	private ObservableList<String> receiverComboList = FXCollections.observableArrayList();

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
		cbReceiver.getSelectionModel().clearSelection();
		cbLedgerType.getSelectionModel().clearSelection();
		amount.clear();
		subjectMatter.clear();
	}

	@FXML
	private void saveTransaction(ActionEvent event) {
		Transaction transaction = new Transaction();
		transaction.setInitiator(getInitiator());
		transaction.setDepartment(getDepartment());
		transaction.setDateOfTransaction(getDateOfTransaction());
		transaction.setReceiver(getReceiver());
		transaction.setLedgerType(getLedgerType());
		transaction.setAmount(new BigDecimal(getAmount()));
		transaction.setSubjectMatter(getSubjectMatter());

		Transaction newTransaction = transactionService.save(transaction);
		loadTransactionDetails();

		saveAlert(newTransaction);
	}

	private void saveAlert(Transaction transaction) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Transaction saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("New entry created by " + transaction.getInitiator() + ". Id - " + transaction.getId());
		alert.showAndWait();
	}

	public String getInitiator() {
		return cbInitiator.getSelectionModel().getSelectedItem();
	}

	public String getDepartment() {
		return cbDepartment.getSelectionModel().getSelectedItem();
	}

	public String getReceiver() {
		return cbReceiver.getSelectionModel().getSelectedItem();
	}

	public String getLedgerType() {
		return cbLedgerType.getSelectionModel().getSelectedItem();
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

	/*
	 * Add All transactions to observable list and update table
	 */
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
		loadReceivers();
		transactionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		// Add all transactions into table
		loadTransactionDetails();
	}

	private void loadInitiators() {
		List<Initiator> initiatorList = initiatorRepository.findAll();
		for (Initiator initiator : initiatorList) {
			intiatorComboList.add(initiator.getName());
		}
		cbInitiator.setItems(intiatorComboList);
	}

	private void loadDepartments() {
		List<Department> departmentList = departmentRepository.findAll();
		for (Department department : departmentList) {
			departmentComboList.add(department.getName());
		}
		cbDepartment.setItems(departmentComboList);
	}

	private void loadLedgers() {
		List<Ledger> ledgerList = ledgerRepository.findAll();
		for (Ledger ledger : ledgerList) {
			ledgerComboList.add(ledger.getName());
		}
		cbLedgerType.setItems(ledgerComboList);
	}

	private void loadReceivers() {
		List<Receiver> receiverList = receiverRepository.findAll();
		for (Receiver receiver : receiverList) {
			receiverComboList.add(receiver.getName());
		}
		cbReceiver.setItems(receiverComboList);
	}

	/*
	 * Set All transactionTable column properties
	 */
	private void setColumnProperties() {
		/*
		 * Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new
		 * StringConverter<LocalDate>() { String pattern = "dd/MM/yyyy";
		 * DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		 * 
		 * @Override public String toString(LocalDate date) { if (date != null) { return
		 * dateFormatter.format(date); } else { return ""; } }
		 * 
		 * @Override public LocalDate fromString(String string) { if (string != null &&
		 * !string.isEmpty()) { return LocalDate.parse(string, dateFormatter); } else {
		 * return null; } } }));
		 */

		colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		colInitiator.setCellValueFactory(new PropertyValueFactory<>("initiator"));
		colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		colDateOfTransaction.setCellValueFactory(new PropertyValueFactory<>("dateOfTransaction"));
		colReceiver.setCellValueFactory(new PropertyValueFactory<>("receiver"));
		colLedgerType.setCellValueFactory(new PropertyValueFactory<>("ledgerType"));
		colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		colSubjectMatter.setCellValueFactory(new PropertyValueFactory<>("subjectMatter"));
		colEdit.setCellFactory(cellFactory);
	}
}
