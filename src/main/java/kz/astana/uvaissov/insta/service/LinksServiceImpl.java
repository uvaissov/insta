package kz.astana.uvaissov.insta.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kz.astana.uvaissov.insta.cabinet.model.ChangePosition;
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
	public List<ProfileUrls> findByProfileInfoIdAndAfterPostion(Long profile_info_id,int position) {
		return repository.findByProfileInfoIdAndAfterPostion(profile_info_id, position);
	}
	
	@Override
	public Integer getMaxPosition(Long profile_info_id) {
		return repository.getMaxPosition(profile_info_id);
	}
	
	@Override
	public List<Object[]> getButtons(Long profile_info_id){
		return em.createNativeQuery("select u.url_id,u.prefix,p.url_value,u.name,u.mobile_prefix,u.icon_url,p.prof_url_id from profile_urls p \r\n" + 
				"join dic_url u on u.url_id=p.url_id\r\n" + 
				"where p.profile_info_id=? order by p.position asc ").setParameter(1, profile_info_id).getResultList();
	}

	@Override
	public void changePosition(Long profile_info_id, ChangePosition model) {
		ProfileUrls from = repository.findByProfileInfoIdAndPostion(profile_info_id, model.from);
		ProfileUrls to = repository.findByProfileInfoIdAndPostion(profile_info_id, model.to);
		from.setPosition(model.to);
		to.setPosition(model.from);
		repository.save(from);
		repository.save(to);
	}
	
}
