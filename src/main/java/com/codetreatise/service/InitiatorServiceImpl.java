package com.codetreatise.service;

import com.codetreatise.bean.Initiator;
import com.codetreatise.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class InitiatorServiceImpl extends GenericService<Initiator> {
	public InitiatorServiceImpl(@Autowired @Qualifier("InitiatorRepository") JpaRepository<Initiator, String> repository) {
		super(repository);
	}
}
