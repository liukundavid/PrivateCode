/**
 * @author Kun Liu
 * The Class of Short Belt, implementing the move method
 */
public class ShortBelt extends Belt {

	public ShortBelt(Animation a, int beltLength) {
		super(a, beltLength);
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
		while (segment[index] != null) {
			wait();
		}

		// insert the element at the specified location
		segment[index] = bag;

		// make a note of the event
		if (bag.isSuspicious()) {
			System.out.println(bag.getId() + " arrived (sus)");
		} else {
			System.out.println(bag.getId() + " arrived");
		}

		// notify any waiting threads that the belt has changed
		notifyAll();
	}
	
	/**
	 * Move the belt along one segment
	 * 
	 * @throws OverloadException
	 *             if there is a bag at position beltLength.
	 * @throws InterruptedException
	 *             if the thread executing is interrupted.
	 */
	public synchronized void move() throws InterruptedException,
			OverloadException {
		// if there is something at the end of the belt, or the belt is empty,
		// or something needs to be picked up for a scan, do not move the belt
		while (isEmpty() || segment[segment.length - 1] != null) {
			wait();
		}

		// double check that a bag cannot fall of the end
		if (segment[segment.length - 1] != null) {
			String message = "Bag fell off end of " + " belt";
			throw new OverloadException(message);
		}
		// move the elements along, making position 0 null
		for (int i = segment.length - 1; i > 0; i--) {
			segment[i] = segment[i - 1];
		}
		segment[0] = null;
		a.animateMoveShortBelt(this);
		// notify any waiting threads that the belt has changed
		notifyAll();
	}
}
