package com.uptech.accounted.repository;

import com.uptech.accounted.bean.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Subledger;
import com.uptech.accounted.bean.SubledgerId;

@Repository("SubledgerRepository")
public interface SubledgerRepository extends JpaRepository<Subledger, SubledgerId> {
}