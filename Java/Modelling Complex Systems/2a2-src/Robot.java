/**
 * @author Kun Liu
 * the robot manipulates belt and scanner
 *
 */
public class Robot extends BaggageHandlingThread {
	protected Scanner scanner;
	protected Belt belt;

	// the amount of time it takes to transfer bag
	protected final static int TRANSFER_TIME = 300;

	public Robot(Belt b, Scanner s) {
		super();
		this.scanner = s;
		this.belt = b;
	}

	// implement the method in subclasses
	public void run() {
		
	}

}
