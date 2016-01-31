package jkulan.software.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;

public class Wro4jCustomXmlModelManagerFactory extends ConfigurableWroManagerFactory {
	private static final Log log = LogFactory.getLog(Wro4jCustomXmlModelManagerFactory.class);

	final private Properties props;

	public Wro4jCustomXmlModelManagerFactory(Properties props) {
		this.props = props;
	}

	@Override
	protected Properties newConfigProperties() {
		return props;
	}

	@Override
	protected WroModelFactory newModelFactory() {
		log.debug("loading from /wro.xml");
		return new XmlModelFactory() {
			@Override
			protected InputStream getModelResourceAsStream() throws IOException {
				String resourceLocation = "/wro.xml";
				log.info(String.format("Loading resource %s", resourceLocation));
				final InputStream stream = getClass().getResourceAsStream(resourceLocation);

				if (stream == null) {
					throw new IOException("Invalid resource requested: " + resourceLocation);
				}

				return stream;
			}
		};
	}
}
