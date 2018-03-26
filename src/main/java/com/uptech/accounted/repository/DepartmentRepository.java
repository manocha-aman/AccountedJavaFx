package com.uptech.accounted.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Department;

import java.util.List;

@Repository("DepartmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, String> {
  List<Department> findAll(Specification<Department> spec);
}
