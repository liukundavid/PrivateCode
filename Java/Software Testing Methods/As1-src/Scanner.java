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
	 * @param b
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
		System.out.println("suspicious Bag set:" + suspiciousBag.toString());
		notifyAll();
	}

	/**
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized Bag getBag() throws InterruptedException {
		Bag bag = null;
		while (suspiciousBag == null || !finished) {
			wait();
		}
		bag = this.suspiciousBag;
		suspiciousBag = null;
		System.out.println("suspicious Bag return:" + bag.toString());
		a.animateScan(suspiciousBag);
//		finished = true;
		notifyAll();
		return bag;
	}

	/**
	 * @throws InterruptedException
	 */
	public synchronized void scan() throws InterruptedException {
		while (suspiciousBag == null || finished) {
			wait();
		}
		System.out.println("Scan Bag ID:" + suspiciousBag.getId());
	}
	
	public synchronized void finish() throws InterruptedException {
		finished = true;
		this.suspiciousBag.clean();
		a.animateScan(suspiciousBag);
		notifyAll();
	}
}
