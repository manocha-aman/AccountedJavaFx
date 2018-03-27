package com.uptech.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Subledger;

@Repository("SubledgerRepository")
public interface SubledgerRepository extends JpaRepository<Subledger, Long> {
}
