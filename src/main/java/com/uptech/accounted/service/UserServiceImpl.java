package com.uptech.accounted.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
  @Value("${admin.password}")
  private String password;


  public boolean authenticate(String user, String password) {
    return user.equals("admin") && password.equals(this.password);
  }
}
