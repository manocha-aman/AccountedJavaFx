package com.uptech.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Recipient;

@Repository("RecipientRepository")
public interface RecipientRepository extends JpaRepository<Recipient, String> {
}
