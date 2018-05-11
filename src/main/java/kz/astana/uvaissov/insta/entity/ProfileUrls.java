package kz.astana.uvaissov.insta.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile_urls")
public class ProfileUrls {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="profile_url_id")
	private Long id;
	
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name="url_id")
	@Column(name="url_id")
	private DicUrl url;
	
	
	
	@Column(name="url_value")
	private String url_value;
	
	@Column(name="profile_info_id")
	private Long profile_info_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DicUrl getUrl() {
		return url;
	}
	public void setUrl(DicUrl url) {
		this.url = url;
	}
	public String getUrl_value() {
		return url_value;
	}
	public void setUrl_value(String url_value) {
		this.url_value = url_value;
	}
	public Long getProfile_info_id() {
		return profile_info_id;
	}
	public void setProfile_info_id(Long profile_info_id) {
		this.profile_info_id = profile_info_id;
	}
	
}