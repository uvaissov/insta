package kz.astana.uvaissov.insta.service;

import java.util.List;

import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.ProfileUrls;

public interface AnalyticsService {

	List<Object[]> getShort(Long profile_info_id);
	
	
}
