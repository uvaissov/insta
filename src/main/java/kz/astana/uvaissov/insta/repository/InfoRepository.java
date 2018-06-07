package kz.astana.uvaissov.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kz.astana.uvaissov.insta.entity.ProfileInfo;


@Repository("infoRepository")
public interface InfoRepository extends JpaRepository<ProfileInfo, Long>{
	ProfileInfo findById(Long id);
	@Query(value="select i.* from profile_info i join \"user\" u on u.profile_info_id=i.id where u.account_name=:profileName LIMIT 1",nativeQuery=true)
	ProfileInfo findByAccountName( @Param("profileName") String profileName);
}