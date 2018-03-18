package com.codetreatise.controller;

import com.codetreatise.bean.Department;
import com.codetreatise.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
