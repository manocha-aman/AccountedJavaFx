package com.accounted.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public class GenericService<T extends Object> {
  final JpaRepository<T, String> repository;

  public GenericService(JpaRepository<T, String> repository) {
    this.repository = repository;
  }

  public T save(T entity) {
    return repository.save(entity);
  }

  public void delete(T entity) {
    repository.delete(entity);
  }

  public T findByCode(String code) {
    return repository.findOne(code);
  }

  public List<T> findAll() {
    return repository.findAll();
  }
}
