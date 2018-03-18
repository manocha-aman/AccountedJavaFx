package com.codetreatise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.User;
import com.codetreatise.repository.UserRepository;

@Service
public class UserServiceImpl {

  private final UserRepository repository;

  public UserServiceImpl(@Autowired UserRepository repository) {
    this.repository = repository;
  }

  public boolean authenticate(String email, String password) {
    return repository.findByEmailAndPassword(email, password) != null;
  }

  public User save(User user) {
    return repository.save(user);
  }
}
