package kz.astana.uvaissov.insta.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.UrlAction;
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
	
	public ProfileInfo findByAccountname(String profileName) {
		return infoRepository.findByAccountName(profileName);
	}
	
	public void setLogo(Long profile_info_id, String fileName) {
		infoRepository.getOne(profile_info_id).setLogo_url(fileName);
	}
}
