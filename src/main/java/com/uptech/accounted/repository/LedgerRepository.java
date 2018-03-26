package com.uptech.accounted.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Ledger;

@Repository("LedgerRepository")
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
  public List<Ledger> findByLedgerCode(String ledgerCode);
}
