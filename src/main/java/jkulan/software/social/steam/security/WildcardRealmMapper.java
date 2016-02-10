package jkulan.software.social.steam.security;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WildcardRealmMapper implements IRealmMapper {

	private static final Log log = LogFactory.getLog(WildcardRealmMapper.class);
	
	@Override
	public String getMapping(String returnToUrl) {
		try {
			URL url = new URL(returnToUrl);
			
			StringBuilder buf = new StringBuilder();
			buf.append(url.getProtocol()).append("://");
			
			String[] hostParts = url.getHost().split("\\.");
			if (hostParts.length <= 2) {
				buf.append(url.getHost());
			} else {
				buf.append("*.").append(hostParts[hostParts.length - 2]).append(".").append(hostParts[hostParts.length - 1]);
			}
			
			if (url.getPort() != -1) {
				buf.append(":").append(url.getPort());
			}
			
			buf.append("/");
			
			return buf.toString();
		} catch (MalformedURLException e) {
			log.warn("returnToUrl not a valid URL", e);
			return null;
		}
	}

}