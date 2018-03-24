package com.uptech.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.Receiver;

@Repository("ReceiverRepository")
public interface ReceiverRepository extends JpaRepository<Receiver, String> {
}
