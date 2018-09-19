package kz.astana.uvaissov.insta.cabinet.model;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.mobile.device.Device;

import com.google.gson.Gson;

import kz.astana.uvaissov.insta.entity.DicUrl;
import kz.astana.uvaissov.insta.util.EncryptionUtil;

public class ButtonContainer {
	private String url,name,iconName,dataType,textPrefix,fullIconUrl;
	private int type = 1;//link
	private long id;
	public ButtonContainer(Object[] row,Device device, Gson gson, Long profileId) {
		Long id = ((BigInteger) row[0]).longValue();
		String prefix = (String) row[1];
		String url = String.format(prefix, (String) row[2]) ;
		String name = (String) row[3];
		if(device.isMobile() && row[4]!=null) {
			prefix = (String) row[4];
		}
		String iconName = (String) row[5];
		
		Map<String,Object> map = new HashMap();
		map.put("id", id.toString());
		map.put("profileId", profileId.toString());
		map.put("url", url);
		this.setId(id);
		this.setUrl(EncryptionUtil.encode(gson.toJson(map)));
		this.name = name;
		this.iconName =iconName;
	}
	public ButtonContainer(DicUrl url2, ServletContext context) {
		this.id = url2.getId();
		this.name = url2.getName();
		this.iconName =url2.getIcon_url();
		this.dataType= url2.getData_type();
		this.textPrefix = url2.getText_prefix();
		this.fullIconUrl = context.getContextPath()+"/assets/svg2/"+this.iconName;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getTextPrefix() {
		return textPrefix;
	}
	public void setTextPrefix(String textPrefix) {
		this.textPrefix = textPrefix;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullIconUrl() {
		return fullIconUrl;
	}
	public void setFullIconUrl(String fullIconUrl) {
		this.fullIconUrl = fullIconUrl;
	}
	
}
