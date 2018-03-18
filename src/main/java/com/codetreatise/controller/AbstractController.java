package com.codetreatise.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.codetreatise.bean.Master;
import com.codetreatise.service.GenericService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class AbstractController<T extends Master, S extends GenericService<T>> implements Initializable {

  private final S service;
  @FXML
  protected TextField code;
  @FXML
  protected TextField name;
  @FXML
  private TableView<T> table;
  @FXML
  private TableColumn<T, String> colCode;
  @FXML
  private TableColumn<T, String> colName;
  private ObservableList<T> list = FXCollections.observableArrayList();

  public AbstractController(S service) {
    this.service = service;
  }

  public void reset(ActionEvent actionEvent) {
    clearFields();
  }

  private void clearFields() {
    name.clear();
    code.clear();
  }

  public void save(ActionEvent actionEvent) {
    T entity = createNewEntity();

    service.save(entity);
    clearFields();
    loadDetails();
  }

  private void loadDetails() {
    list.clear();
    list.addAll(service.findAll());

    table.setItems(list);
    table.setVisible(true);
  }

  protected abstract T createNewEntity();

  public void delete(ActionEvent actionEvent) {
    T selectedItem = table.getSelectionModel().getSelectedItem();
    service.delete(selectedItem);
    loadDetails();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    table.setEditable(false);
    setColumnProperties();
    table.setOnMouseClicked(event -> {
      T selectedItem = table.getSelectionModel().getSelectedItem();
      code.setText(selectedItem.getCode());
      name.setText(selectedItem.getName());
    });
    loadDetails();
  }

  private void setColumnProperties() {
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
  }



}
