package com.uptech.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Department;

@Repository("DepartmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, String> {
}
