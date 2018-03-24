package com.uptech.accounted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Ledger;
import com.uptech.accounted.service.LedgerServiceImpl;

@Controller
public class LedgerController extends AbstractController<Ledger, LedgerServiceImpl> {

  public LedgerController(@Autowired LedgerServiceImpl service) {
    super(service);
  }

  @Override
  protected Ledger createNewEntity() {
    return new Ledger(code.getText(), name.getText());
  }
}
