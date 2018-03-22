package com.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accounted.bean.Transaction;
import com.accounted.repository.TransactionRepository;

@Service
public class TransactionServiceImpl {

  private final TransactionRepository repository;

  public TransactionServiceImpl(@Autowired TransactionRepository repository) {
    this.repository = repository;
  }

  public Transaction save(Transaction user) {
    return repository.save(user);
  }
}
