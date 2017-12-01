package refrigerant;

public enum ESaturationTable {

	   //# Temp.   Press       Press     Density  Density          Enthalpy           Entropy  Entropy
		//#  °C      kPa         kPa       kg/m3    kg/m3     kJ/kg   kJ/kg  kJ/kg    kJ/kg K  kJ/kg K
		//#        liquid        gas      liquid     gas      Liquid  latent  gas      liquid    gas

		Temperature,
		PressureL,
		PressureG,
		DensityL,
		DensityG,
		EnthalpyL,
		EnthalpyG,
		EntropyL,
		EntropyG;
		
		public int col() {
			return this.ordinal();

		}
		
}
