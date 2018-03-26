package com.uptech.accounted.repository;

import com.uptech.accounted.bean.Initiator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.User;

import java.util.List;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, String> {
}