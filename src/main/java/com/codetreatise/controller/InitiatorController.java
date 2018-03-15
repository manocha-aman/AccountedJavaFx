package com.codetreatise.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Initiator;
import com.codetreatise.repository.InitiatorRepository;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

@Controller
public class InitiatorController implements Initializable {
	@FXML
	private TextField id;
	@FXML
	private TextField code;
	@FXML
	private TextField name;
	@Autowired
	private InitiatorRepository initiatorRepository;

	public void reset(ActionEvent actionEvent) {
	}

	public void saveInitiator(ActionEvent actionEvent) {
		System.out.println("SAVING INITIATOR");
		Initiator initiator = new Initiator();
		try {
			initiator = new Initiator(new Long(id.getText()), code.getText(), name.getText());
		}
		catch(Exception exception) {
			exception.getMessage();
		}
		initiatorRepository.save(initiator);
	}

	public void deleteInitiators(ActionEvent actionEvent) {
		initiatorRepository.delete(initiatorRepository.findOne(id.getText()));
	}

	public void updateInitiator(ActionEvent actionEvent) {
		// initiatorRepository.
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
