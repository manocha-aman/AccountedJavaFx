package com.uptech.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Recipient;

@Service
public class RecipientServiceImpl extends GenericService<Recipient> {

  public RecipientServiceImpl(@Autowired @Qualifier("RecipientRepository") JpaRepository<Recipient, String> repository) {
    super(repository);
  }
}
