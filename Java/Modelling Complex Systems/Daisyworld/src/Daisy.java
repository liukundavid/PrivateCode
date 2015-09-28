/**
 * @author Kun Liu
 *
 * @Purpose this class extends from Turtle, and differs from open ground as
 *          Daisy has a special attribute, age.
 */
public class Daisy extends Turtle {
	public int age;

	/**
	 * Constructor
	 * @param _albedo
	 */
	public Daisy(double _albedo) {
		// inherit from Turtle class
		super(_albedo);
		this.age = 0;
	}
}
