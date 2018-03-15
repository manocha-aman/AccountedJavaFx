package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Initiator;

@Repository
public interface InitiatorRepository extends JpaRepository<Initiator, String> {
	
}
