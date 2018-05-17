package kz.astana.uvaissov.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.astana.uvaissov.insta.entity.DicUrl;
import kz.astana.uvaissov.insta.entity.ProfileInfo;


@Repository("dicRepository")
public interface DicRepository extends JpaRepository<DicUrl, Long>{
	DicUrl findById(long id);
}