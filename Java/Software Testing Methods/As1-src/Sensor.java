/*
 * A sensor identify the bag at segment 3 as along as often as possible
 * 
 */
public class Sensor {
	// the belt to be handled
	// protected Belt belt;

	/**
	 * Create a new Sensor with a belt to move
	 */
	public Sensor() {
		// this.belt = belt;
	}

	public boolean detectSuspiciousBag(Bag bag) {
		if(bag == null) {
			return true;
		} else if (!bag.isClean()) {
			System.out.println("Sensor:" + bag.toString());
			return false;
		} else if (bag.isClean()) {
			return true;
		} else {
			return true;
		}
	}
}
