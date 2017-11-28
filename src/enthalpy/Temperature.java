package enthalpy;

public class Temperature {

	/*
	 * 
			P
			
			+
			|
			|                 T
			|                 
			|                 |
			|                 |
			|                 |
			|                 |
			|                 |      XXXXXXXXXX
			|                 |  XXXX         XXXX
			|       H(SatL)   |XXX               XXX
			|       P(SatL)  X+---                  XX
			|               XX    \------            XXX
			|               X            \----         X
			|              XX                 \----    X   H(SatG)
			|             XX                       \----   P(SatG)
			|             X                             \---
			|             X                             X   \
			|             X                             X    ----
			|                                           X        \-
			|                                                      \
			|
			|
			+---------------------------------------------------------------+     H
			                             H
	 */
	double HSat_L;
	double PSat_L;
	double HSat_G;
	double PSat_G;
	
}
