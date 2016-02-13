package jkulan.software.configuration;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;
//
/**
 * Wro4J integration
 * stolen from http://blog.instea.sk/2015/08/spring-boot-configuration-of-wro4j/ 
 * @author fuero
 */
@Configuration
public class Wro4JConfiguration {
	private Log log = LogFactory.getLog(getClass());
	
	@Bean
	FilterRegistrationBean webResourceOptimizer(Environment env) {
		log.debug("Init wro4j filter config");
		FilterRegistrationBean fr = new FilterRegistrationBean();
		ConfigurableWroFilter filter = new ConfigurableWroFilter();
		Properties props = buildWroProperties(env);
		filter.setWroManagerFactory(new Wro4jCustomXmlModelManagerFactory(props));
		filter.setProperties(props);
		fr.setFilter(filter);
		fr.addUrlPatterns("/wro/*");
		return fr;
	}

	private static final String[] OTHER_WRO_PROP = new String[] { ConfigurableProcessorsFactory.PARAM_PRE_PROCESSORS,
			ConfigurableProcessorsFactory.PARAM_POST_PROCESSORS };

	private Properties buildWroProperties(Environment env) {
		Properties prop = new Properties();
		for (ConfigConstants c : ConfigConstants.values()) {
			addProperty(env, prop, c.name());
		}
		for (String name : OTHER_WRO_PROP) {
			addProperty(env, prop, name);
		}
		log.debug(String.format("Wro4J properties %s", prop));
		return prop;
	}

	private void addProperty(Environment env, Properties to, String name) {
		String value = env.getProperty("wro." + name);
		if (value != null) {
			to.put(name, value);
		}
	}
}
