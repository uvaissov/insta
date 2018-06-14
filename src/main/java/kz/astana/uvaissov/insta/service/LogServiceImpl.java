package kz.astana.uvaissov.insta.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.LogAction;
import kz.astana.uvaissov.insta.repository.LogRepository;

@Service("logService")
public class LogServiceImpl implements LogService{

	
	@Autowired
	private LogRepository logRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void save(LogAction action) {
		logRepository.save(action);
		
	}
}
