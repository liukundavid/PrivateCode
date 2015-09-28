public class ScannerHandler extends BaggageHandlingThread {
	// the belt to be handled
	protected Scanner scanner;

	// the amount of time it takes to move the belt
	protected final static int CLEAN_TIME = 5000;

	/**
	 * Create a new BeltMover with a belt to move
	 */
	public ScannerHandler(Scanner scanner) {
		super();
		this.scanner = scanner;
	}

	/**
	 * Move the belt as often as possible, but only if there is a bag on the
	 * belt which is not in the last position.
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
