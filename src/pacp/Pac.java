package pacp;

public class Pac {
	
	protected Scroll scroll;
	private Circulator circulator;
	private Condenser condenser;
	private Evaporator evaporator;
	
	// Constructor
	public Pac() {
		setScroll(new Scroll());
	}

	// Getter and Setter
	public Scroll getScroll() {
		return scroll;
	}

	public void setScroll(Scroll scroll) {
		this.scroll = scroll;
	}

	public Circulator getCirculator() {
		return circulator;
	}

	public void setCirculator(Circulator circulator) {
		this.circulator = circulator;
	}

	public Condenser getCondenser() {
		return condenser;
	}

	public void setCondenser(Condenser condenser) {
		this.condenser = condenser;
	}

	public Evaporator getEvaporator() {
		return evaporator;
	}

	public void setEvaporator(Evaporator evaporator) {
		this.evaporator = evaporator;
	}
	
}
