package com.uptech.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Transaction;

@Repository("TransactionRepository")
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, String>, QueryDslPredicateExecutor<Transaction> {
  public Transaction findByTransactionId(long transactionId);
}
