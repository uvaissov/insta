package kz.astana.uvaissov.insta.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "profile_urls")
public class ProfileUrls {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="prof_url_id")
	private Long id;
	
	@Column(name="url_value")
	@NotNull
	private String url_value;
	
	@Column(name="url_title")
	private String url_title;
	
	@Column(name="profile_info_id")
	@NotNull
	private Long profile_info_id;
	
	@Column(name="url_id")
	@NotNull
	private Long url_id;
	
	@Column(name="position")
	@NotNull
	private int position;
	
	
	public Long getUrl_id() {
		return url_id;
	}
	public void setUrl_id(Long url_id) {
		this.url_id = url_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl_value() {
		return url_value;
	}
	public void setUrl_value(String url_value) {
		this.url_value = url_value;
	}
	
	public String getUrl_title() {
		return url_title;
	}
	public void setUrl_title(String url_title) {
		this.url_title = url_title;
	}
	public Long getProfile_info_id() {
		return profile_info_id;
	}
	public void setProfile_info_id(Long profile_info_id) {
		this.profile_info_id = profile_info_id;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
}