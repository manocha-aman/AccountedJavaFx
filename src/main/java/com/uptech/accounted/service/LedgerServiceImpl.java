package com.uptech.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Ledger;

@Service
public class LedgerServiceImpl extends GenericService<Ledger> {

  public LedgerServiceImpl(@Autowired @Qualifier("LedgerRepository") JpaRepository<Ledger, String> repository) {
    super(repository);
  }
}
