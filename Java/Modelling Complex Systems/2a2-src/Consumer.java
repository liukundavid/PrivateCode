import java.util.Random;

/**
 * A consumer continually tries to take bags from the end of a belt
 */

public class Consumer extends BaggageHandlingThread {

	// the maximum amount of time the consumer waits
	protected final static int MAX_SLEEP = 2800;

	// the belt from which the consumer takes the bags
	protected Belt belt;
	protected Belt shortBelt;

	/**
	 * Create a new Consumer that consumes from a belt
	 */
	public Consumer(Belt belt, Belt shortBelt) {
		super();
		this.belt = belt;
		this.shortBelt = shortBelt;
	}

	/**
	 * Loop indefinitely trying to get bags from the baggage belt
	 */
	public void run() {
		while (!isInterrupted()) {
			try {
				Bag bag = null;
				
				// consume the bags in main belt first
				// if the last segment has no bags, then
				// consume the bags in the short belt
				if (belt.peek(belt.length() - 1) != null) {
					bag = belt.getEndBelt();
				}
				else if (shortBelt.peek(shortBelt.length() - 1) != null) {
					bag = shortBelt.getEndBelt();
				}

				// let some time pass ...
				Random random = new Random();
				int sleepTime = 500 + random.nextInt(MAX_SLEEP - 500);
				sleep(sleepTime);

			} catch (InterruptedException e) {
				this.interrupt();
			}
		}

		System.out.println("Consumer terminated");
	}
}
