package kz.astana.uvaissov.insta.service;

import kz.astana.uvaissov.insta.entity.ProfileInfo;

public interface InfoService {
	public void save(ProfileInfo info);
	public ProfileInfo findByInfoId(Long infoId);
	public ProfileInfo findByAccountname(String profileName);
	public void setLogo(Long profile_info_id, String fileName);
}
