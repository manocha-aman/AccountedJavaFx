package com.uptech.accounted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.uptech.accounted.bean.SubjectMatter;

@Service
public class SubjectMatterServiceImpl extends GenericService<SubjectMatter> {

  public SubjectMatterServiceImpl(
      @Autowired @Qualifier("SubjectMatterRepository") JpaRepository<SubjectMatter, String> repository) {
    super(repository);
  }
}
