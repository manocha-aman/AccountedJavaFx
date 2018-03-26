package com.uptech.accounted.repository;

import com.uptech.accounted.bean.Initiator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Transaction;

import java.util.List;

@Repository("TransactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, String> {
  List<Transaction> findAll(Specification<Transaction> spec);
}
