package testinglog4j;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Bar {
	static final Logger logger = LogManager.getLogger(Bar.class.getName());

	public boolean doIt() {
		logger.error("Did it again!");
		logger.trace("trace (Bar)");
		logger.debug("debug (Bar)");
		return false;
	}
}
