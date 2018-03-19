package com.accounted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.accounted.bean.Initiator;
import com.accounted.service.InitiatorServiceImpl;

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