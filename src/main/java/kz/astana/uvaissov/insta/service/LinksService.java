package kz.astana.uvaissov.insta.service;

import java.util.List;

import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;

public interface LinksService {

	ProfileUrls findById(Long id);

	List<ProfileUrls> findByProfileInfoId(Long profile_info_id);

	void remove(ProfileUrls urls);

	void save(ProfileUrls prof);

	List<Object[]> getButtons(Long profile_info_id);
	
	
}
