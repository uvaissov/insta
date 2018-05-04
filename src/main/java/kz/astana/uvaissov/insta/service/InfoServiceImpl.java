package kz.astana.uvaissov.insta.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.repository.InfoRepository;

@Service("infoService")
public class InfoServiceImpl implements InfoService{

	
	@Autowired
	private InfoRepository infoRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public ProfileInfo findByInfoId(Long infoId) {
		return infoRepository.findById(infoId);
	}

	@Override
	public void save(ProfileInfo info) {
		infoRepository.save(info);
		
	}
	
	public ProfileInfo findByProfilename(String profileName) {
		return infoRepository.findByProfilename(profileName);
	}
}
