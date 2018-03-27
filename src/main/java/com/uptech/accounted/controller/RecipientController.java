package com.uptech.accounted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Recipient;
import com.uptech.accounted.service.RecipientServiceImpl;

@Controller
public class RecipientController extends AbstractController<Recipient, RecipientServiceImpl> {

  public RecipientController(@Autowired RecipientServiceImpl service) {
    super(service);
  }

  @Override
  protected Recipient createNewEntity() {
    return new Recipient(code.getText(), name.getText());
  }
}
