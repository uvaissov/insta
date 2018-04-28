package kz.astana.uvaissov.insta.cabinet.model;

public class NavItem {
	private String itemName;
	private String itemPage;
	private boolean active = false;
	private String position;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemPage() {
		return itemPage;
	}
	public void setItemPage(String itemPage) {
		this.itemPage = itemPage;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public NavItem(String itemName, String itemPage, boolean active,String position) {
		super();
		this.itemName = itemName;
		this.itemPage = itemPage;
		this.active = active;
		this.position = position;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
