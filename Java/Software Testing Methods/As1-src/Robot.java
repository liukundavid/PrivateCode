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

	public void run() {
		Bag suspiciousBag;
		Bag cleanedBag;
		while (!interrupted()) {
				try {
					synchronized (scanner) {
						// take suspicious bag from belt
						suspiciousBag = belt.getSuspiciousBag();
						sleep(TRANSFER_TIME);
						scanner.setBag(suspiciousBag);
						
						// take cleaned bag from scanner
						cleanedBag = scanner.getBag();
						sleep(TRANSFER_TIME);
						belt.put(cleanedBag, 2);
//						scanner.a.animateScan(scanner.suspiciousBag);
						System.out.println("cleaned Bag return:" + cleanedBag.toString());
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
