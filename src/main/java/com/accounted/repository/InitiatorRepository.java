package com.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accounted.bean.Initiator;

@Repository("InitiatorRepository")
public interface InitiatorRepository extends JpaRepository<Initiator, String> {
}
