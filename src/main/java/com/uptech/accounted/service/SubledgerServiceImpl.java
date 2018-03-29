package com.uptech.accounted.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.Subledger;

@Service
public class SubledgerServiceImpl {

  final JpaRepository<Subledger, Long> subledgerRepository;

  public SubledgerServiceImpl(JpaRepository<Subledger, Long> subledgerRepository) {
    this.subledgerRepository = subledgerRepository;
  }

  public Subledger save(Subledger subledger) {
    return subledgerRepository.save(subledger);
  }

  public void delete(Subledger subledger) {
    subledgerRepository.delete(subledger);
  }

  public Subledger findById(long id) {
    return subledgerRepository.findOne(id);
  }

  public List<Subledger> findAll() {
    return subledgerRepository.findAll();
  }
}
