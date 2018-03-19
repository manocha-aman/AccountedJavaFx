package com.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.accounted.bean.Department;

@Service
public class DepartmentServiceImpl extends GenericService<Department> {

  public DepartmentServiceImpl(@Autowired @Qualifier("DepartmentRepository") JpaRepository<Department, String> repository) {
    super(repository);
  }
}
