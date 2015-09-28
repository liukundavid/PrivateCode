/**
 * The Scanner, can only handle one bag at one time
 */
public class Scanner {
	// the suspicious bag to be scanned
	protected Bag suspiciousBag;

	// the animation to keep up to date
	protected Animation a;
	
	// identify the scanner has finished scanning
	protected boolean finished;

	/**
	 * Create a new, empty scanner, initialized as empty 
	 */
	public Scanner(Animation a) {
		this.suspiciousBag = null;
		this.a = a;
		finished = true;
	}

	/**
	 * Get suspicious bag from robot
	 * 
	 * @param b
	 *            the suspicious bag from main robot
	 * @throws InterruptedException
	 */
	public synchronized void setBag(Bag b) throws InterruptedException {
		// while there is another bag in the way, block this thread
		while (suspiciousBag != null || !finished) {
			wait();
		}
		this.suspiciousBag = b;
		a.animateScan(suspiciousBag);
		finished = false;
		System.out.println("suspicious Bag get from robot:" + suspiciousBag.toString());
		notifyAll();
	}

	/**
	 * Get the cleaned bag from scanner
	 * 
	 * @return the cleaned bag
	 * @throws InterruptedException
	 */
	public synchronized Bag getBag() throws InterruptedException {
		Bag bag = null;
		while (suspiciousBag == null || !finished) {
			wait();
		}
		bag = this.suspiciousBag;
		suspiciousBag = null;
		System.out.println("suspicious Bag return to robot:" + bag.toString());
		a.animateScan(suspiciousBag);
//		finished = true;
		notifyAll();
		return bag;
	}

	/**
	 * scan the suspicious bag
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void scan() throws InterruptedException {
		while (suspiciousBag == null || finished) {
			wait();
		}
		System.out.println("Scan Bag ID:" + suspiciousBag.getId());
	}
	
	/**
	 * clean the suspicious bag and finish scanning
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void finish() throws InterruptedException {
		finished = true;
		this.suspiciousBag.clean();
		a.animateScan(suspiciousBag);
		notifyAll();
	}
}
