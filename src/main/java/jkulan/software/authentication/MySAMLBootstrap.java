package jkulan.software.authentication;

import org.opensaml.Configuration;
import org.opensaml.xml.security.BasicSecurityConfiguration;
import org.opensaml.xml.signature.SignatureConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.security.saml.SAMLBootstrap;

/**
 * Force SHA-256 signatures instead of the default SHA-1 signatures
 * @author fuero
 */
public class MySAMLBootstrap extends SAMLBootstrap {
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	    super.postProcessBeanFactory(beanFactory);
	    BasicSecurityConfiguration config = (BasicSecurityConfiguration) Configuration.getGlobalSecurityConfiguration();
	    config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA256);
	}
}
