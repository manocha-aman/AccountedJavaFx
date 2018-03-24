package com.uptech.accounted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.Department;
import com.uptech.accounted.service.DepartmentServiceImpl;

@Controller
public class DepartmentController extends AbstractController<Department, DepartmentServiceImpl> {

  public DepartmentController(@Autowired DepartmentServiceImpl service) {
    super(service);
  }

  @Override
  protected Department createNewEntity() {
    return new Department(code.getText(), name.getText());
  }
}
