package com.uptech.accounted.utils;

import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ColumnHijriDateFormatter<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private final DateTimeFormatter format;

    public ColumnHijriDateFormatter(DateTimeFormatter format) {
        super();
        this.format = format;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> arg0) {
        return new TableCell<S, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    LocalDate ld = (LocalDate) item;
                    HijrahDate date = HijrahChronology.INSTANCE.date(ld);
                    
                    String val = date.format(format); //ld.format(format);
                    setGraphic(new Label(val));
                }
            }
        };
    }
}