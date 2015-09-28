/**
 * @author Kun Liu
 * handle the scanning
 */
public class ScannerHandler extends BaggageHandlingThread {
	// the scanner to clean the suspicious bag
	protected Scanner scanner;

	// the amount of time it takes to clean bag
	protected final static int CLEAN_TIME = 3000;

	/**
	 * Create a new scanner handler with a scanner
	 */
	public ScannerHandler(Scanner scanner) {
		super();
		this.scanner = scanner;
	}

	/**
	 * Scan the bag in scanner as often as possible 
	 */
	public void run() {
		while (!isInterrupted()) {
			try {
				// start scanning
				scanner.scan();
				// wait scanning
				Thread.sleep(CLEAN_TIME);
				// finish scanning
				scanner.finish();
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}

		System.out.println("Scanner terminated");
	}
}
