/**
 * @author Kun Liu 
 * The baggage belt, 
 * super class of main belt and short belt
 */
public class Belt {

	// the items in the belt segments
	protected Bag[] segment;

	// the length of this belt
	protected int beltLength;
	
	// the animation to keep up to date
	protected Animation a;

	// the sensor on belt
	protected Sensor sensor;

	// Sensor Setter
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	// the belt type (for including in exception messages)
	// protected String type;
	final private static String indentation = "                 ";

	/**
	 * Initializer
	 * Create a new, empty belt, initialized as empty
	 * @param a		
	 * 			An Animation Object to draw belt
	 * @param beltLength  
	 * 			the volume of the belt
	 */
	public Belt(Animation a, int beltLength) {
		this.beltLength = beltLength;
		segment = new Bag[beltLength];
		for (int i = 0; i < segment.length; i++) {
			segment[i] = null;
		}
		this.a = a;
		this.sensor = null;
	}

	/**
	 * Put a bag on the belt.
	 * 
	 * @param bag
	 *            the bag to put onto the belt.
	 * @param index
	 *            the place to put the bag
	 * @param beltId
	 *            the source where the bag comes from
	 * @throws InterruptedException
	 *             if the thread executing is interrupted.
	 */
	public synchronized void put(Bag bag, int index)
			throws InterruptedException {
		// while there is another bag in the way, block this thread
//		while (segment[index] != null) {
//			wait();
//		}
//
//		// insert the element at the specified location
//		segment[index] = bag;
//
//		// make a note of the event
//		if (bag.isSuspicious()) {
//			System.out.println(bag.getId() + " arrived (sus)");
//		} else {
//			System.out.println(bag.getId() + " arrived");
//		}
//
//		// notify any waiting threads that the belt has changed
//		notifyAll();
	}

	/**
	 * Take a bag off the end of the belt
	 * 
	 * @return the removed bag
	 * @throws InterruptedException
	 *             if the thread executing is interrupted
	 */
	public synchronized Bag getEndBelt() throws InterruptedException {

		Bag bag;

		while (segment[segment.length - 1] == null) {
			wait();
		}

		// get the next item
		bag = segment[segment.length - 1];
		segment[segment.length - 1] = null;

		// make a note of the event
		System.out.print(indentation);
		if (!bag.isClean()) {
			System.out.println(bag.getId() + " departed -- unclean!!!");
		} else if (bag.isSuspicious()) {
			System.out.println("Suspicious Bag:" + String.valueOf(bag.getId())
					+ " departed -- clean!!!");
		} else {
			System.out.println(bag.getId() + " departed");
		}

		// notify any waiting threads that the belt has changed
		notifyAll();
		return bag;
	}

	/**
	 * Move the belt along one segment
	 * the subclasses should implement this method
	 * in subclass, due to different animation.
	 * 
	 * @throws OverloadException
	 *             if there is a bag at position beltLength.
	 * @throws InterruptedException
	 *             if the thread executing is interrupted.
	 */
	public synchronized void move() throws InterruptedException,
			OverloadException {

	}

	/**
	 * @return the maximum size of this belt
	 */
	public int length() {
		return beltLength;
	}

	/**
	 * Peek at what is at a specified segment
	 * 
	 * @param index
	 *            the index at which to peek
	 * @return the bag in the segment (or null if the segment is empty)
	 */
	public Bag peek(int index) {
		Bag result = null;
		if (index >= 0 && index < beltLength) {
			result = segment[index];
		}
		return result;
	}

	// check whether the belt is currently empty
	protected boolean isEmpty() {
		for (int i = 0; i < segment.length; i++) {
			if (segment[i] != null) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return java.util.Arrays.toString(segment);
	}

	/*
	 * @return the final position on the belt
	 */
	public int getEndPos() {
		return beltLength - 1;
	}
}
