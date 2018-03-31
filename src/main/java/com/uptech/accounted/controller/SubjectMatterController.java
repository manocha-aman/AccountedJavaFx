package com.uptech.accounted.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uptech.accounted.bean.SubjectMatter;
import com.uptech.accounted.service.SubjectMatterServiceImpl;

@Controller
public class SubjectMatterController extends AbstractController<SubjectMatter, SubjectMatterServiceImpl> {

    public SubjectMatterController(@Autowired SubjectMatterServiceImpl service) {
      super(service);
    }

    @Override
    protected SubjectMatter createNewEntity() {
      return new SubjectMatter(code.getText(), name.getText());
    }
  }