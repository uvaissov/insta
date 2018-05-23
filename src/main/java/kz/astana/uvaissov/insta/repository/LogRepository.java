package kz.astana.uvaissov.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.astana.uvaissov.insta.entity.ProfileInfo;
import kz.astana.uvaissov.insta.entity.UrlAction;


@Repository("logRepository")
public interface LogRepository extends JpaRepository<UrlAction, Long>{
	
}