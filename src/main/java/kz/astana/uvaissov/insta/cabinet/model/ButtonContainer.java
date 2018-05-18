package kz.astana.uvaissov.insta.cabinet.model;

import java.math.BigInteger;

public class ButtonContainer {
	private String prefix,value,name,iconName;
	private int type = 1;//link
	private long id;
	public ButtonContainer(Object[] row) {
		this.id = ((BigInteger) row[0]).longValue();
		this.prefix = (String) row[1];
		this.value = (String) row[2];
		this.name = (String) row[3];
		this.iconName =(String) row[3];
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	
}
