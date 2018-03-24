package com.uptech.accounted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Receiver;
import com.uptech.accounted.service.ReceiverServiceImpl;

@Controller
public class ReceiverController extends AbstractController<Receiver, ReceiverServiceImpl> {

  public ReceiverController(@Autowired ReceiverServiceImpl service) {
    super(service);
  }

  @Override
  protected Receiver createNewEntity() {
    return new Receiver(code.getText(), name.getText());
  }
}
