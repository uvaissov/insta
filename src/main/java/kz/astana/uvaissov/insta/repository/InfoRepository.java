package kz.astana.uvaissov.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.astana.uvaissov.insta.entity.ProfileInfo;


@Repository("infoRepository")
public interface InfoRepository extends JpaRepository<ProfileInfo, Long>{
	ProfileInfo findById(Long id);
	ProfileInfo findByProfilename(String profileName);
	
}