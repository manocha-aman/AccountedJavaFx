package com.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accounted.bean.Ledger;

@Repository("LedgerRepository")
public interface LedgerRepository extends JpaRepository<Ledger, String> {
}
