/**
 * @author Kun Liu the robot manipulates the main belt and scanner
 *
 */
public class MainRobot extends Robot {

	public MainRobot(Belt b, Scanner s) {
		super(b, s);
	}

	// Get bag from main belt to scanner belt as often as possible
	public void run() {
		Bag suspiciousBag;
		MainBelt mainBelt = (MainBelt) belt;
		while (!interrupted()) {
			try {
				// take suspicious bag from belt only if the scanner
				// has finished scanning
				synchronized (scanner) {
					if (scanner.suspiciousBag == null) {
						synchronized (mainBelt) {
							mainBelt.isScannerAvailable = true;
							mainBelt.notifyAll();
							suspiciousBag = mainBelt.getSuspiciousBag();
						}
						System.out.println("Suspicious:"
								+ suspiciousBag.toString()
								+ " has been got by Robot");

						// robot spent some time on transferring bag from belt
						// to scanner
						sleep(TRANSFER_TIME);

						// scanner get bag from robot
						scanner.setBag(suspiciousBag);
					}
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
