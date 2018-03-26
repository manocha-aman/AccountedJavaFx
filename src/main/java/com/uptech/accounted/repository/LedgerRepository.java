package com.uptech.accounted.repository;

import com.uptech.accounted.bean.Department;
import com.uptech.accounted.bean.Ledger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LedgerRepository")
public interface LedgerRepository extends JpaRepository<Ledger, String> {
  List<Department> findAll(Specification<Ledger> spec);
}
