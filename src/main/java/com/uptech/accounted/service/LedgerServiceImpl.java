package com.uptech.accounted.service;

import java.util.List;

import com.uptech.accounted.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Ledger;

@Service
public class LedgerServiceImpl {

  final LedgerRepository ledgerRepository;

  @Autowired
  public LedgerServiceImpl(LedgerRepository ledgerRepository) {
    this.ledgerRepository = ledgerRepository;
  }

  public Ledger save(Ledger ledger) {
    return ledgerRepository.save(ledger);
  }

  public void delete(Ledger ledger) {
    ledgerRepository.delete(ledger);
  }

  public Ledger findByCode(String id) {
    return ledgerRepository.findOne(id);
  }

  public List<Ledger> findAll() {
    return ledgerRepository.findAll();
  }
}
