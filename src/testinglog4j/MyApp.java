package testinglog4j;

/* ============================================================================================
/ log4j2.xml file must be under src folder in order to be able to find it on the classpath !!
 * ============================================================================================= */

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MyApp {

	// Define a static logger variable so that it references the
	private static final Logger logger = LogManager.getLogger(MyApp.class);

	public static void main(final String... args) {

		logger.trace("trace");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		logger.fatal("fatal");


		logger.trace("Entering application.");
		Bar bar = new Bar();
		if (!bar.doIt()) {
			logger.error("Didn't do it.");
		}
		logger.trace("Exiting application.");
	}
}

