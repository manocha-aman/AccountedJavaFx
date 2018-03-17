package com.codetreatise.service;

import com.codetreatise.bean.Initiator;
import com.codetreatise.generic.GenericService;

public interface InitiatorService extends GenericService<Initiator>{

	Initiator findByName(String name);

	boolean contains(String code);

}
