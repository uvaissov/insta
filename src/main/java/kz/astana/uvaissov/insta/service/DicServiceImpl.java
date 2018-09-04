package kz.astana.uvaissov.insta.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.DicUrl;
import kz.astana.uvaissov.insta.repository.DicRepository;

@Service("idicService")
public class DicServiceImpl implements DicService{

	
	@Autowired
	private DicRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public DicUrl findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void save(DicUrl info) {
		repository.save(info);
		
	}
	
	@Override
	public List<DicUrl> getUrls(){
		return repository.findAll(new Sort(Sort.Direction.ASC,"id")); 
	}
	
}
