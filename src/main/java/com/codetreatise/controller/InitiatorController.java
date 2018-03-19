package com.codetreatise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Initiator;
import com.codetreatise.service.InitiatorServiceImpl;

@Controller
public class InitiatorController extends AbstractController<Initiator, InitiatorServiceImpl> {

  public InitiatorController(@Autowired InitiatorServiceImpl service) {
    super(service);
  }

  @Override
  protected Initiator createNewEntity() {
    return new Initiator(code.getText(), name.getText());
  }
}