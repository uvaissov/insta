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

@Service("analyticsService")
public class AnalyticsServiceImpl implements AnalyticsService{

	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Object[]> getShort(Long profile_info_id){
		return em.createNativeQuery("select 'total' as \"time\",count(a.id) from log_actions a where a.profile_info_id=?\r\n" + 
				"union all\r\n" + 
				"select 'week',count(a.id) from log_actions a where a.profile_info_id=? and a.action_datetime > (now() - interval '1 week')\r\n" + 
				"union all\r\n" + 
				"select 'day',count(a.id) from log_actions a where a.profile_info_id=? and a.action_datetime > (now() - interval '1 day')").setParameter(1, profile_info_id).setParameter(2, profile_info_id).setParameter(3, profile_info_id).getResultList();
	}
}
