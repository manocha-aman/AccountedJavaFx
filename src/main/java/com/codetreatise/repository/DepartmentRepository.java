package com.codetreatise.repository;

import com.codetreatise.bean.Department;
import com.codetreatise.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DepartmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, String> {
}
