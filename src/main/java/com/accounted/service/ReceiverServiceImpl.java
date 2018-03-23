package com.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.accounted.bean.Receiver;

@Service
public class ReceiverServiceImpl extends GenericService<Receiver> {

  public ReceiverServiceImpl(@Autowired @Qualifier("ReceiverRepository") JpaRepository<Receiver, String> repository) {
    super(repository);
  }
}
