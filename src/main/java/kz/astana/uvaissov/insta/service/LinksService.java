package kz.astana.uvaissov.insta.service;

import java.util.List;

import kz.astana.uvaissov.insta.cabinet.model.ChangePosition;
import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;

public interface LinksService {

	ProfileUrls findById(Long id);

	List<ProfileUrls> findByProfileInfoId(Long profile_info_id);

	void remove(ProfileUrls urls);

	void save(ProfileUrls prof);

	List<Object[]> getButtons(Long profile_info_id);

	List<ProfileUrls> findByProfileInfoIdAndAfterPostion(Long profile_info_id, int position);

	Integer getMaxPosition(Long profile_info_id);

	void changePosition(Long profile_info_id, ChangePosition model);
	
	
}
