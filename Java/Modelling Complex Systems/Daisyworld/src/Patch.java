/**
 * @author Kun Liu
 *
 * @Purpose Define Patch class. This Class has three attributes: Turtle,
 *          current_temperature, numOfNeighbour.
 */

public class Patch {
	public Turtle turtle;
	public double current_temperature;
	public int numOfNeighbour;

	public Patch() {

	}

	/**
	 * Constructor
	 * 
	 * @param _albedo
	 * @param type
	 */
	public Patch(double _albedo, TurtleType type) {

		if (TurtleType.OpenGround == type) {
			turtle = new Turtle(_albedo);
			turtle.type = 0;
		} else {
			turtle = new Daisy(_albedo);
			if (TurtleType.WhiteDaisy == type) {
				turtle.type = 1;
			} else {
				turtle.type = 2;
			}
		}
		numOfNeighbour = 0;
		current_temperature = 0;
	}

	/**
	 * calculate the temperature of current patch based on the specific
	 * luminosity
	 * 
	 * @param luminosity
	 */
	public void calculate_temperature(double luminosity) {
		double absorbed_luminosity = 0;
		double local_heating = 0;

		// the percentage of absorbed energy is calculated (1 - albedo)
		// and then multiplied by the solar-luminosity to give a scaled
		// absorbed-luminosity.
		absorbed_luminosity = (1 - this.turtle.getAlbedo()) * luminosity;

		// local-heating is calculated as logarithmic function of
		// solar-luminosity
		// where a absorbed-luminosity of 1 yields a local-heating of 80 degrees
		// C
		// and an absorbed-luminosity of .5 yields a local-heating of
		// approximately 30 C
		// and a absorbed-luminosity of 0.01 yields a local-heating of
		// approximately -273 C
		if (absorbed_luminosity > 0) {
			local_heating = 72 * Math.log(absorbed_luminosity) + 80;
		} else {
			local_heating = 80;
		}

		// set the temperature at this patch to be the average of the current
		// temperature
		// and the local-heating effect
		current_temperature = (current_temperature + local_heating) / 2;
	}

}
