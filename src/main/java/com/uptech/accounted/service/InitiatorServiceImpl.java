package com.uptech.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Initiator;

@Service
public class InitiatorServiceImpl extends GenericService<Initiator> {

  public InitiatorServiceImpl(@Autowired @Qualifier("InitiatorRepository") JpaRepository<Initiator, String> repository) {
    super(repository);
  }
}
