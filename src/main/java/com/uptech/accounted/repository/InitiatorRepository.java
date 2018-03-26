package com.uptech.accounted.repository;

import com.uptech.accounted.bean.Initiator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("InitiatorRepository")
public interface InitiatorRepository extends JpaRepository<Initiator, String> {
  List<Initiator> findAll(Specification<Initiator> spec);
}
