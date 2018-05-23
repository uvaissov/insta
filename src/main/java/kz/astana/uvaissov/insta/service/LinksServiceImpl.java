package kz.astana.uvaissov.insta.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.entity.DicUrl;
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
	public void remove(ProfileUrls urls) {
		repository.delete(urls);
	}
	
	@Override
	public List<ProfileUrls> findByProfileInfoId(Long profile_info_id) {
		return repository.findByProfileInfoId(profile_info_id);
	}
	
	@Override
	public List<Object[]> getButtons(Long profile_info_id){
		return em.createNativeQuery("select u.url_id,u.prefix,p.url_value,u.name,u.mobile_prefix from profile_urls p \r\n" + 
				"join dic_url u on u.url_id=p.url_id\r\n" + 
				"where p.profile_info_id=? ").setParameter(1, profile_info_id).getResultList();
	}
}
