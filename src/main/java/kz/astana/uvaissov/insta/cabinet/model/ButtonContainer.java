package kz.astana.uvaissov.insta.cabinet.model;

import java.math.BigInteger;

import org.springframework.mobile.device.Device;

import kz.astana.uvaissov.insta.util.EncryptionUtil;

public class ButtonContainer {
	private String url,name,iconName;
	private int type = 1;//link
	public ButtonContainer(Object[] row,Device device) {
		Long id = ((BigInteger) row[0]).longValue();
		
		String prefix = (String) row[1];
		if(device.isMobile() && row[4]!=null) {
			prefix = (String) row[4];
		}
		
		String url = String.format(prefix, (String) row[2]) ;
		String json = "{\"url\":\""+url+ "\",\"id\":"+id+"}";
		this.setUrl(EncryptionUtil.encode(json));
		this.name = (String) row[3];
		this.iconName =(String) row[3];
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
