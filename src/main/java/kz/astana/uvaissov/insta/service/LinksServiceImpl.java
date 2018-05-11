package kz.astana.uvaissov.insta.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;
import kz.astana.uvaissov.insta.repository.InfoRepository;
import kz.astana.uvaissov.insta.repository.LinksRepository;

@Service("linksService")
public class LinksServiceImpl implements LinksService{

	
	@Autowired
	private LinksRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public ProfileUrls findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void save(ProfileUrls prof) {
		repository.save(prof);
		
	}
	@Override
	public List<ProfileUrls> findByProfileInfoId(Long profile_info_id) {
		return repository.findByProfileInfoId(profile_info_id);
	}
}
