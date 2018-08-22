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
	@Column(name="description")
	private String description;
	@Column(name="profilename")
	private String profilename;
	@Column(name="background")
	private String background;
	@Column(name="logo_url")
	private String logo_url;
	@Column(name="background_url")
	private String background_url;
	@Column(name="design_type")
	private String design_type;
	@Column(name="background_svg")
	private String background_svg;
	@Column(name="element_color")
	private String element_color;
	@Column(name="background_color")
	private String background_color;

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
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getBackground_url() {
		return background_url;
	}
	public void setBackground_url(String background_url) {
		this.background_url = background_url;
	}
	public String getDesign_type() {
		return design_type;
	}
	public void setDesign_type(String design_type) {
		this.design_type = design_type;
	}
	public String getBackground_svg() {
		return background_svg;
	}
	public void setBackground_svg(String background_svg) {
		this.background_svg = background_svg;
	}
	public String getElement_color() {
		return element_color;
	}
	public void setElement_color(String element_color) {
		this.element_color = element_color;
	}
	public String getBackground_color() {
		return background_color;
	}
	public void setBackground_color(String background_color) {
		this.background_color = background_color;
	}
	@Override
	public String toString() {
		return "ProfileInfo [id=" + id + ", description=" + description + ", profilename=" + profilename
				+ ", background=" + background + ", logo_url=" + logo_url + ", background_url=" + background_url + "]";
	}
	
}