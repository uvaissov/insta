package kz.astana.uvaissov.insta.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profile_info")
public class ProfileInfo {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="profilename")
	private String profilename;
	@Column(name="description")
	private String description;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProfilename() {
		return profilename;
	}
	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}