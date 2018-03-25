package com.uptech.accounted.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Ledger;

@Service
public class LedgerServiceImpl {

  final JpaRepository<Ledger, Long> ledgerRepository;

  public LedgerServiceImpl(JpaRepository<Ledger, Long> ledgerRepository) {
    this.ledgerRepository = ledgerRepository;
  }

  public Ledger save(Ledger ledger) {
    return ledgerRepository.save(ledger);
  }

  public void delete(Ledger ledger) {
    ledgerRepository.delete(ledger);
  }

  public Ledger findByCode(long id) {
    return ledgerRepository.findOne(id);
  }

  public List<Ledger> findAll() {
    return ledgerRepository.findAll();
  }
}
