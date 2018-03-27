package com.uptech.accounted.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Transaction;
import com.uptech.accounted.repository.TransactionRepository;

@Service
public class TransactionServiceImpl {

  private final TransactionRepository repository;

  public TransactionServiceImpl(@Autowired TransactionRepository repository) {
    this.repository = repository;
  }

  public List<Transaction> findAll() {
    return repository.findAll();
  }

  public Transaction save(Transaction user) {
    return repository.save(user);
  }

  public void delete(Transaction transaction) {
    repository.delete(transaction);
  }
}
