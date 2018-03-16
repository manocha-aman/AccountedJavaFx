package com.codetreatise.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Initiator;
import com.codetreatise.bean.User;
import com.codetreatise.repository.InitiatorRepository;
import com.codetreatise.service.InitiatorService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Controller
public class InitiatorController implements Initializable {
	@FXML
	private TextField id;
	@FXML
	private Label lblInitiator;
	@FXML
	private Button reset;
	@FXML
	private Button saveInitiator;
	@FXML
	private TableColumn<Initiator, Long> colId;
	@FXML
	private TableColumn<Initiator, Long> colCode;
	@FXML
	private TableColumn<Initiator, String> colName;
	@FXML
	private MenuItem deleteInitiators;
	@FXML
	private TextField code;
	@FXML
	private TextField name;
	@Autowired
	private InitiatorRepository initiatorRepository;

	@Autowired
	private InitiatorService initiatorService;

	private ObservableList<Initiator> initiatorList = FXCollections.observableArrayList();

	@FXML
	private TableView<Initiator> initiatorTable;

	public void reset(ActionEvent actionEvent) {
	}

	// public void saveInitiator(ActionEvent actionEvent) {
	// System.out.println("SAVING INITIATOR");
	// Initiator initiator = new Initiator();
	// try {
	// initiator = new Initiator(new Long(id.getText()), code.getText(),
	// name.getText());
	// } catch (Exception exception) {
	// exception.getMessage();
	// }
	// initiatorRepository.save(initiator);
	// }

	public void deleteInitiators(ActionEvent actionEvent) {
		initiatorRepository.delete(Long.parseLong(getId().getText()));
	}

	public void updateInitiator(ActionEvent actionEvent) {
		// initiatorRepository.
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initiatorTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setColumnProperties();
		loadInitiatorDetails();
	}

	private void setColumnProperties() {
		    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		    colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
	}

	@FXML
	private void saveInitiator(ActionEvent event) {

		if (validate("Name", getName().getText(), "[a-zA-Z]+") && validate("id", getId().getText(), "[0-9]+")
				&& validate("code", getCode().getText(), "[0-9]+")) {

			String text = id.getText();
			if (text == null || text == "") {
				if (validate("Name", getName().getText(), "[a-zA-Z]+")
						&& emptyValidation("Code", getCode().getText().isEmpty())) {

					Initiator initiator = new Initiator();
					initiator.setName(getName().getText());
					initiator.setCode(getCode().getText());

					Initiator newInitiator = initiatorService.save(initiator);

					saveAlert(newInitiator);
				}

			} else {
				Initiator initiator = new Initiator();
				initiator.setName(getName().getText());
				initiator.setCode(getCode().getText());

				Initiator newInitiator = initiatorService.save(initiator);

				saveAlert(newInitiator);
				// Initiator initiator = initiatorService.find(Long.parseLong(text));
				// initiator.setName(getName().getText());
				// initiator.setCode(getCode().getText());
				// Initiator updatedInitiator = initiatorService.update(initiator);
				// updateAlert(updatedInitiator);
			}

			clearFields();
			loadInitiatorDetails();
		}

	}

	private void loadInitiatorDetails() {
		initiatorList.clear();
		initiatorList.addAll(initiatorService.findAll());
		initiatorTable.setItems(initiatorList);
	}

	private void clearFields() {
		id.setText(null);
		name.clear();
		code.clear();
	}

	private void updateAlert(Initiator initiator) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Initiator updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The initiator " + initiator.getName() + " has been updated.");
		alert.showAndWait();
	}

	private void saveAlert(Initiator newInitiator) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Initiator saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The initiator " + newInitiator.getName() + " has been created with code : "
				+ newInitiator.getCode() + ".");
		alert.showAndWait();

	}

	/*
	 * Validations {Needs to be moved to a common code. Duplicacy for every form}
	 */
	private boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (field.equals("Role"))
			alert.setContentText("Please Select " + field);
		else {
			if (empty)
				alert.setContentText("Please Enter " + field);
			else
				alert.setContentText("Please Enter Valid " + field);
		}
		alert.showAndWait();
	}

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	public TextField getId() {
		return id;
	}

	public void setId(TextField id) {
		this.id = id;
	}

	public TextField getCode() {
		return code;
	}

	public void setCode(TextField code) {
		this.code = code;
	}

	public TextField getName() {
		return name;
	}

	public void setName(TextField name) {
		this.name = name;
	}
}