package kz.astana.uvaissov.insta.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "dic_url")
public class DicUrl {
	@Id
	@Column(name="url_id")
	private long id;
	@Column(name="name")
	@NotNull
	private String name;
	@Column(name="abbr")
	private String abbr;
	@Column(name="prefix")
	@NotNull
	private String prefix;
	@Column(name="mobile_prefix")
	private String mobile_prefix;
	@Column(name="icon_url")
	@NotNull
	private String icon_url;
	@Column(name="text_prefix")
	private String text_prefix;
	@Column(name="data_type")
	@ColumnDefault("account")
	@NotNull
	private String data_type;
	
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
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getText_prefix() {
		return text_prefix;
	}
	public void setText_prefix(String text_prefix) {
		this.text_prefix = text_prefix;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	
}