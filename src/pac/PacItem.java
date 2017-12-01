package pac;


public enum PacItem {

	// --------------------------------------------------------------------
	// Pac class is a list of different elements like: Compressors ,Condensers ..
	// To simulate a complete Cycle, we have to know which of the element has to be chosen 
	// Compressor[0] or Compressor[1],..
	// These Enumeration will help to choose the right one
	// id[COMP] = 0  or id[COMP] = 1
	// --------------------------------------------------------------------
	COMP,
	COND,
	DEHY,
	EPVA,
	EVAP,
	CRCLS,
	CIRTS,
	CRCLD,
	CIRTD;

	
	/**
	 * Will return the position of the element
	 * @return
	 */
	public int pos() {
		return this.ordinal();
	}
	
	public int nb() {
		return this.ordinal();
	}

}
