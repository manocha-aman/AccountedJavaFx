package com.codetreatise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Initiator;
import com.codetreatise.repository.InitiatorRepository;
import com.codetreatise.service.InitiatorService;

@Service
public class InitiatorServiceImpl implements InitiatorService {
	
	@Autowired
	private InitiatorRepository initiatorRepository;
	
	@Override
	public Initiator save(Initiator entity) {
		return initiatorRepository.save(entity);
	}

	@Override
	public Initiator update(Initiator entity) {
		return initiatorRepository.save(entity);
	}

	@Override
	public void delete(Initiator entity) {
		initiatorRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		initiatorRepository.delete(id);
	}

	@Override
	public Initiator find(Long id) {
		return initiatorRepository.findOne(id);
	}

	@Override
	public List<Initiator> findAll() {
		return initiatorRepository.findAll();
	}

	@Override
	public Initiator findByName(String name) {
		return initiatorRepository.findByName(name);
	}

	@Override
	public void deleteInBatch(List<Initiator> Initiators) {
		initiatorRepository.deleteInBatch(Initiators);
	}

	@Override
	public boolean contains(String code) {
		boolean result = false;
		for (Initiator initiator : initiatorRepository.findAll()) {
			if(initiator.getCode().equalsIgnoreCase(code.toString()))
				return true;
		}
		return result;
	}

}
