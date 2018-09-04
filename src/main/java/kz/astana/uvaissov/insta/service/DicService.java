package kz.astana.uvaissov.insta.service;

import java.util.List;

import kz.astana.uvaissov.insta.entity.DicUrl;
import kz.astana.uvaissov.insta.entity.ProfileInfo;

public interface DicService {

	DicUrl findById(Long id);

	void save(DicUrl info);

	List<DicUrl> getUrls();
}
