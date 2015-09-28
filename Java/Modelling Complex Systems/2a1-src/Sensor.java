/*
 * A sensor identify the bag at segment 3 as along as often as possible
 * 
 */
public class Sensor {
	// the belt to be handled
	// protected Belt belt;

	/**
	 * Create a new Sensor
	 */
	public Sensor() {
		
	}

	/**
	 * @param bag
	 * 			the bag in segement 3
	 * @return the boolean value that identify 
	 * 		   whether the bag is clean or not
	 */
	public boolean detectSuspiciousBag(Bag bag) {
		if(bag == null) {
			return false;
		} else if (!bag.isClean()) {
			System.out.println("Sensor:" + bag.toString());
			return true;
		} else if (bag.isClean()) {
			return false;
		} else {
			return false;
		}
	}
}
