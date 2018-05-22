package kz.astana.uvaissov.insta.util;

public enum SitePreference {

    /**
     * The user prefers the 'normal' site.
     */
    NORMAL,
     
    /**
     * The user prefers the 'mobile' site.
     */
    MOBILE {		
        public boolean isMobile() {
            return true;
        }
    };
    
    /**
     * Tests if this is the 'mobile' SitePreference.
     * Designed to support concise SitePreference boolean expressions e.g. <c:if test="${currentSitePreference.mobile}"></c:if>.
     */
    public boolean isMobile() {
        return false;
    }
	
}
