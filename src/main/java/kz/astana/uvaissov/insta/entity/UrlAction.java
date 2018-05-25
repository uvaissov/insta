package kz.astana.uvaissov.insta.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "url_actions")
public class UrlAction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="profile_info_id")
	private long profileInfoId;
	@Column(name="url_id")
	private long urlId;
	@Column(name="action_datetime")
	private Timestamp actionDatetime;
	@Column(name="is_mobile")
	private boolean mobile;
	@Column(name="mobile_os")
	private String mobileOs;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getProfileInfoId() {
		return profileInfoId;
	}
	public void setProfileInfoId(long profileInfoId) {
		this.profileInfoId = profileInfoId;
	}
	public long getUrlId() {
		return urlId;
	}
	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}
	public Timestamp getActionDatetime() {
		return actionDatetime;
	}
	public void setActionDatetime(Timestamp actionDatetime) {
		this.actionDatetime = actionDatetime;
	}
	public boolean isMobile() {
		return mobile;
	}
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
	public String getMobileOs() {
		return mobileOs;
	}
	public void setMobileOs(String mobileOs) {
		this.mobileOs = mobileOs;
	}
	
}