package jkulan.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.client.SAML2ClientConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.JWSAlgorithm;

import jkulan.auth.SteamClient;

@Configuration
@ComponentScan(basePackages = "org.pac4j.springframework.web")
public class Pac4JConfiguration {
	private static final Log log = LogFactory.getLog(Pac4JConfiguration.class);
	
	@Bean
	@ConfigurationProperties("saml")
	public SAML2ClientConfiguration saml2Config() {
		final SAML2ClientConfiguration config = new SAML2ClientConfiguration();
		System.out.println(config);
		log.trace(String.format("SAML2 Config %s", config.toString()));
		return config;
	}
	
	@Bean
	public SAML2Client saml2Client(SAML2ClientConfiguration config) {
		final SAML2Client saml2Client = new SAML2Client(config);
		return saml2Client;
	}
	
	@Bean
	public SteamClient steamClient() {
		final SteamClient steamClient = new SteamClient();
		return steamClient;
	}
	
	@Bean
	@Qualifier("googleClient")
	@ConfigurationProperties("google")
	public OidcClient googleClient() {
		final OidcClient oidcClient = new OidcClient();
		oidcClient.setPreferredJwsAlgorithm(JWSAlgorithm.PS384);
        oidcClient.addCustomParam("prompt", "consent");
		return oidcClient;
	}
	
	@Bean
	@Qualifier("pac4j-config")
	public Config config(SAML2Client saml2Client) {
		final Config config = new Config();
		final Clients clients = new Clients(saml2Client, steamClient(), googleClient());
		clients.setCallbackUrl("http://localhost:8080/callback");
		config.setClients(clients);
		return config;
	}
}
