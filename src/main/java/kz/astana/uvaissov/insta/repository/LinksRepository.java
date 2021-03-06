package kz.astana.uvaissov.insta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kz.astana.uvaissov.insta.entity.ProfileUrls;


@Repository("linksRepository")
public interface LinksRepository extends JpaRepository<ProfileUrls, Long>{
	ProfileUrls findById(Long id);
	
	@Query("select u from ProfileUrls u where u.profile_info_id=:profile_info_id order by u.position asc")
	List<ProfileUrls> findByProfileInfoId(@Param("profile_info_id") Long profile_info_id);
	
	@Query("select u from ProfileUrls u where u.profile_info_id=:profile_info_id and u.position>=:position order by u.position asc")
	List<ProfileUrls> findByProfileInfoIdAndAfterPostion(@Param("profile_info_id") Long profile_info_id,@Param("position") int position);
	
	@Query("select max(u.position) from ProfileUrls u where u.profile_info_id=:profile_info_id ")
	Integer getMaxPosition(@Param("profile_info_id") Long profile_info_id );
	
	
	@Query("select u from ProfileUrls u where u.profile_info_id=:profile_info_id and u.position=:position")
	ProfileUrls findByProfileInfoIdAndPostion(@Param("profile_info_id") Long profile_info_id,@Param("position") int position);
}