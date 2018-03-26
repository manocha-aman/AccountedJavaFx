package com.uptech.accounted.repository;

import com.uptech.accounted.bean.Receiver;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ReceiverRepository")
public interface ReceiverRepository extends JpaRepository<Receiver, String> {
  List<Receiver> findAll(Specification<Receiver> spec);
}
