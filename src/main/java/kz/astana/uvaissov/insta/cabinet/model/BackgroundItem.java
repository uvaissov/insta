package kz.astana.uvaissov.insta.cabinet.model;

public class BackgroundItem {
	
	public BackgroundItem(String name, boolean active) {
		super();
		this.name = name;
		this.active = active;
	}
	private String name;
	private String body;
	private boolean active = false;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
