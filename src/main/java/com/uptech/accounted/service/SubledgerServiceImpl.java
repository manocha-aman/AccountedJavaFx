package com.uptech.accounted.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Subledger;
import com.uptech.accounted.bean.SubledgerId;
import com.uptech.accounted.repository.SubledgerRepository;

@Service
public class SubledgerServiceImpl {

  @Autowired
  final SubledgerRepository subledgerRepository;

  public SubledgerServiceImpl(SubledgerRepository subledgerRepository) {
    this.subledgerRepository = subledgerRepository;
  }

  public Subledger save(Subledger subledger) {
    return subledgerRepository.save(subledger);
  }

  public void delete(Subledger subledger) {
    subledgerRepository.delete(subledger);
  }

  public List<Subledger> findByLedgerCode(long ledgerCode) {
    List<Subledger> allSubledgers = subledgerRepository.findAll();
    List<Subledger> subledgersByLedgerCode = new ArrayList<>();
    for (Subledger subledger : allSubledgers) {
      if (subledger.getSubledgerId().getLedgerCode() == ledgerCode) {
        subledgersByLedgerCode.add(subledger);
      }
    }
    return subledgersByLedgerCode;
  }

  public Subledger findByLedgerAndSubledgerCode(long ledgerCode, long subledgerCode) {
    return subledgerRepository.findOne(new SubledgerId(ledgerCode, subledgerCode));
  }

  public List<Subledger> findAll() {
    return subledgerRepository.findAll();
  }
}
