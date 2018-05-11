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
	private long url_id;
	@Column(name="name")
	private String name;
	@Column(name="abbr")
	private String abbr;
	@Column(name="prefix")
	private String prefix;
	public long getUrl_id() {
		return url_id;
	}
	public void setUrl_id(long url_id) {
		this.url_id = url_id;
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