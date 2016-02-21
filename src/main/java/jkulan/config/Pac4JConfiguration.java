package jkulan.config;

import com.nimbusds.jose.JWSAlgorithm;
import jkulan.software.auth.SteamClient;
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

/**
 * Initializes all pac4j clients and registers them with its config class.
 * @author fuero
 *
 */
@Configuration
// Spring Boot Autoconfig doesn't scan this package, so we have to force this.
@ComponentScan(basePackages = "org.pac4j.springframework.web")
public class Pac4JConfiguration {
	/**
	 * The SAML2 client needs an extra bean to load its config.
	 * @return The config bean
	 */
	@Bean
	@ConfigurationProperties("saml")
	public SAML2ClientConfiguration saml2Config() {
		return new SAML2ClientConfiguration();
	}
	
	@Bean
	public SAML2Client saml2Client(SAML2ClientConfiguration config) {
		return new SAML2Client(config);
	}
	
	@Bean
	public SteamClient steamClient() {
		return new SteamClient();
	}

	/**
	 * Reads clientID and secret from Spring Boot's config store
	 * and initializes the OpenID Connect client for Google.
	 * Qualifiers are needed, as this class is loaded multiple times for
	 * multiple OpenID Connect IDPs.
	 * @return the OpenID Connect client for Google
	 */
	@Bean
	@Qualifier("googleClient")
	@ConfigurationProperties("google")
	public OidcClient googleClient() {
		final OidcClient oidcClient = new OidcClient();
		oidcClient.setPreferredJwsAlgorithm(JWSAlgorithm.PS384);
        oidcClient.addCustomParam("prompt", "consent");
		oidcClient.setName("googleClient");
		return oidcClient;
	}
	
	/**
	 * Assembles pac4j's config, registering all clients.
	 * @param saml2Client autowiring
	 * @return The config to be used in {@link SecurityConfiguration}
	 */
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
