/**
 * @author Kun Liu
 *
 * @Purpose This class stands for white daisy, black daisy and open ground in
 *          this case as they have some common attributes(albedo and type). Due
 *          to a specific attribute of the daisies, another class "Daisy" is
 *          extended to represent the daisies.
 */
// Define the turtle type
enum TurtleType {
	OpenGround, WhiteDaisy, BlackDaisy
}

public class Turtle {
	private double albedo;
	public int type;

	
	/**
	 * Constructor
	 * @param _albedo
	 */
	public Turtle(double _albedo) {
		this.setAlbedo(_albedo);
	}

	// Setter
	public double getAlbedo() {
		return albedo;
	}

	// Getter
	public void setAlbedo(double albedo) {
		this.albedo = albedo;
	}

	/**
	 * Convert TurtleType to digit
	 * @return 0 if Openground
	 * 		   1 if WhiteDaisy
	 * 		   2 if BlackDaisy
	 */
	public TurtleType toTurtleType() {
		if (this.type == 0) {
			return TurtleType.OpenGround;
		} else if (this.type == 1) {
			return TurtleType.WhiteDaisy;
		} else {
			return TurtleType.BlackDaisy;
		}
	}

}