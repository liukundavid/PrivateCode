/**
 * @author Kun Liu
 * 
 */
public class SubRobot extends Robot {

	public SubRobot(Belt b, Scanner s) {
		super(b, s);

	}

	// Get bag from scanner to short belt as often as possible
	public void run() {
		Bag cleanedBag;
		while (!interrupted()) {
			try {
				// take cleaned bag from scanner
				// only when the short belt is empty 
				// or the first segment is available
				synchronized (belt) {
					if (belt.peek(0) == null) {
						cleanedBag = scanner.getBag();
						System.out.println("cleaned:" + cleanedBag.toString()
								+ " has been got by Robot");
						
						// robot spends some time on transferring bag
						sleep(TRANSFER_TIME);
						belt.put(cleanedBag, 0);
						System.out.println("cleaned Bag return:"
								+ cleanedBag.toString());
					}
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
