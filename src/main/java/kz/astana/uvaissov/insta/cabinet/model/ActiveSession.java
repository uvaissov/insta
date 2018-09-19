package kz.astana.uvaissov.insta.cabinet.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ActiveSession {
	public long profileId =-1;
	public String logoUrl;
	public String backgroundUrl;
	public String userName;
}
