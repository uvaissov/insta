package kz.astana.uvaissov.insta.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dic_url")
public class DicUrl {
	@Id
	@Column(name="url_id")
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="abbr")
	private String abbr;
	@Column(name="prefix")
	private String prefix;
	@Column(name="mobile_prefix")
	private String mobile_prefix;
	public String getMobile_prefix() {
		return mobile_prefix;
	}
	public void setMobile_prefix(String mobile_prefix) {
		this.mobile_prefix = mobile_prefix;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	
}