package com.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accounted.bean.Transaction;

@Repository("TransactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, String> {
  public Transaction findById(long id);
}
