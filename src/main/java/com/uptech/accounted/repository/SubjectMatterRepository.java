package com.uptech.accounted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uptech.accounted.bean.SubjectMatter;

@Repository("SubjectMatterRepository")
public interface SubjectMatterRepository extends JpaRepository<SubjectMatter, String> {
}
