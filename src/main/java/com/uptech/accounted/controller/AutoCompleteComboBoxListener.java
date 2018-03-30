package com.uptech.accounted.controller;

import com.uptech.accounted.bean.Master;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public  class AutoCompleteComboBoxListener<T extends Master> implements EventHandler<KeyEvent> {
    private final List<T> items;
    private final ComboBox<String> comboBox;
    private String selected = "";
    public AutoCompleteComboBoxListener(ComboBox<String> cbInitiator, List<T> items) {
      this.comboBox = cbInitiator;
      this.items = items;
    }

    @Override
    public void handle(KeyEvent event) {
      if( event.getCode() == KeyCode.BACK_SPACE) {
        if (!selected.isEmpty())
          selected = selected.substring(0, selected.length() - 1);
      }
      else {
        selected += event.getText();
      }
      for( T item: items ) {
        if( item.getName().toLowerCase().startsWith(selected) ||
            item.getCode().toLowerCase().startsWith(
                selected) ) comboBox.getSelectionModel().select(item.getCode() + "-" + item.getName() );
      }
    }
  }