package com.codetreatise.controller;

import com.codetreatise.bean.Department;
import com.codetreatise.repository.DepartmentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DepartmentController  implements Initializable {
  @FXML
  private TextField id;
  @FXML
  private TextField code;
  @FXML
  private TextField name;
  @Autowired private DepartmentRepository departmentRepository;

  public void reset(ActionEvent actionEvent) {
  }

  public void saveDepartment(ActionEvent actionEvent) {
	  System.out.println("Saving Department");
    Department department = new Department(new Long(id.getText()), code.getText(), name.getText());

    departmentRepository.save(department);
  }

  public void deleteDepartments(ActionEvent actionEvent) {
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }
}
