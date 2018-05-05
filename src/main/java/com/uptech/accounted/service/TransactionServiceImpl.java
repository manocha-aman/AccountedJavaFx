package com.uptech.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Transaction;
import com.uptech.accounted.repository.TransactionRepository;

@Service
public class TransactionServiceImpl {

  private final TransactionRepository repository;

  public TransactionServiceImpl(@Autowired TransactionRepository repository) {
    this.repository = repository;
  }

  public Iterable<Transaction> findAll(PageRequest pageRequest) {
    return repository.findAll(pageRequest);
  }

  public Transaction save(Transaction user) {
    return repository.save(user);
  }

  public void delete(Transaction transaction) {
    repository.delete(transaction);
  }

  public Transaction findById(Long id) {
    return repository.findByTransactionId(id);
  }

  public long count() {
    return repository.count();
  }
}
