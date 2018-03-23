package com.accounted.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accounted.bean.User;
import com.accounted.repository.UserRepository;

@Service
public class UserServiceImpl {

  private final UserRepository repository;

  public UserServiceImpl(@Autowired UserRepository repository) {
    this.repository = repository;
  }

  public boolean authenticate(String email, String password) {
//    return repository.findByEmailAndPassword(email, password) != null;
	  return true;
  }

  public List<User> findAll() {
	  return repository.findAll();
  }
  
  public User save(User user) {
    return repository.save(user);
  }
}
