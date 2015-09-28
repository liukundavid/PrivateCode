import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Kun Liu
 * Create GUI components of the systems 
 *
 */
public class Animation {

	private static JPanel mainPanel = new JPanel();
	private static LabelPanel labelPanel = new LabelPanel();
	private static BagPanel bagPanel = new BagPanel();
	private static ScanPanel scanPanel = new ScanPanel();

	final private static String BAG_PATH = "images/brown_bag.png";
	final private static String SUS_BAG_PATH = "images/purple_bag.png";
	final private static String CLEAN_BAG_PATH = "images/white_bag.png";

	final private static int FRAME_X = 1000;
	final private static int FRAME_Y = 500;
	final private static int PANEL_X = 1000;
	final private static int PANEL_Y = 160;

	final protected static int BELT_SEGMENTS = 5;
	final protected static int BELT_Y = 15;
	final protected static int BELT_HEIGHT = 50;
	final protected static int SEG_LENGTH = 150;
	final protected static int SEG_START = 100;
	final protected static int SEG1_X = 300;
	final protected static int SEG2_X = SEG1_X + SEG_LENGTH;
	final protected static int SEG3_X = SEG2_X + SEG_LENGTH;
	final protected static int SEG4_X = SEG3_X + SEG_LENGTH;
	final protected static int SEG5_X = SEG4_X + SEG_LENGTH;

	final protected static int SCANNER_WIDTH = 160;
	final protected static int SCANNER_HEIGHT = 140;

	public Animation() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowAnimation();
			}
		});
	}

	public static class BeltMoveListener implements ActionListener {

		final private static int STEP = 4;
		private static int offset = 0;

		public void actionPerformed(ActionEvent e) {
			if (offset < SEG_LENGTH) {
				bagPanel.setLocation(offset, 0);
				offset += STEP;
			} else {
				((Timer) e.getSource()).stop();
				offset = 0;
			}
		}
	}

	public static class ScannerListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			scanPanel.repaint();
			((Timer) e.getSource()).stop();
		}
	}

	private static void createAndShowAnimation() {

		JFrame f = new JFrame("The baggage handling system");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.WHITE);
		f.setSize(new Dimension(FRAME_X, FRAME_Y));

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		bagPanel.setSize(new Dimension(PANEL_X, PANEL_Y));
		labelPanel.setSize(new Dimension(PANEL_X, PANEL_Y));
		scanPanel.setSize(new Dimension(PANEL_X, PANEL_Y));
		mainPanel.add(bagPanel);
		mainPanel.add(labelPanel);
		mainPanel.add(scanPanel);

		f.add(mainPanel);
		f.setVisible(true);
	}
	
	// invoked by the main belt, to move the bags in the main belt
	public static void animateMove(Belt belt) {

		bagPanel.setBeltData(belt);
		Timer timer = new Timer(1000 / 60, new BeltMoveListener());
		timer.start();
	}

	// update the bag state in Scanner
	public synchronized void animateScan(Bag suspiciousBag) {
		scanPanel.setSuspiciousBag(suspiciousBag);
		Timer timer = new Timer(1000 / 60, new ScannerListener());
		timer.start();
	}

	private static class LabelPanel extends JPanel {

		public LabelPanel() {
			super();
			setBackground(Color.WHITE);
		}

		@Override
		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			int width = getWidth();
			int height = getHeight();
			super.paintComponent(g2);

			g2.drawString("Segment", SEG_START, BELT_Y);
			g2.drawString("1", SEG1_X, BELT_Y);
			g2.drawString("2", SEG2_X, BELT_Y);
			g2.drawString("3", SEG3_X, BELT_Y);
			g2.drawString("4", SEG4_X, BELT_Y);
			g2.drawString("5", SEG5_X, BELT_Y);
		}
	}

	private static class BagPanel extends JPanel {

		private Belt belt = new Belt(null);
		private Image normalImg;
		private Image susImg;
		private Image cleanImg;

		public BagPanel() {
			super();
			setBackground(Color.WHITE);
			normalImg = null;
			susImg = null;
			cleanImg = null;

			try {
				normalImg = ImageIO.read(new File(BAG_PATH));
			} catch (java.io.IOException e) {
				// if file cannot be opened, print message and exit
				System.err.println("File " + BAG_PATH + " cannot be opened");
				System.exit(1);
			}

			try {
				susImg = ImageIO.read(new File(SUS_BAG_PATH));
			} catch (java.io.IOException e) {
				// if file cannot be opened, print message and exit
				System.err
						.println("File " + SUS_BAG_PATH + " cannot be opened");
				System.exit(1);
			}

			try {
				cleanImg = ImageIO.read(new File(CLEAN_BAG_PATH));
			} catch (java.io.IOException e) {
				// if file cannot be opened, print message and exit
				System.err.println("File " + CLEAN_BAG_PATH
						+ " cannot be opened");
				System.exit(1);
			}
		}

		@Override
		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			int width = getWidth();
			int height = getHeight();
			super.paintComponent(g2);

			// draw the bags

			for (int i = 0; i < BELT_SEGMENTS; i++) {
				if (belt.peek(i) != null) {
					Bag bag = belt.peek(i);
					if (bag.isSuspicious() && !bag.isClean()) {
						g2.drawImage(susImg, SEG_START + i * SEG_LENGTH,
								BELT_HEIGHT, null);
					} else if (bag.isSuspicious() && bag.isClean()) {
						g2.drawImage(cleanImg, SEG_START + i * SEG_LENGTH,
								BELT_HEIGHT, null);
					} else {
						g2.drawImage(normalImg, SEG_START + i * SEG_LENGTH,
								BELT_HEIGHT, null);
					}
					g2.drawString(String.valueOf(belt.peek(i).toString()),
							150 + 150 * i, 30);
				}
			}
		}

		public void setBeltData(Belt belt) {
			this.belt = belt;
		}
	}

	private static class ScanPanel extends JPanel {
		private Bag suspiciousBag = null;
		private Image susImg;
		private Image cleanImg;

		public ScanPanel() {
			super();
			setBackground(Color.WHITE);
			susImg = null;
			cleanImg = null;

			try {
				susImg = ImageIO.read(new File(SUS_BAG_PATH));
			} catch (java.io.IOException e) {
				// if file cannot be opened, print message and exit
				System.err
						.println("File " + SUS_BAG_PATH + " cannot be opened");
				System.exit(1);
			}

			try {
				cleanImg = ImageIO.read(new File(CLEAN_BAG_PATH));
			} catch (java.io.IOException e) {
				// if file cannot be opened, print message and exit
				System.err.println("File " + CLEAN_BAG_PATH
						+ " cannot be opened");
				System.exit(1);
			}
		}

		@Override
		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			int width = getWidth();
			int height = getHeight();
			super.paintComponent(g2);

			g2.drawString("Scanner", SEG2_X, SCANNER_HEIGHT / 2);
			g2.draw(new Rectangle.Float(SEG3_X - SCANNER_WIDTH / 2, 0,
					SCANNER_WIDTH, SCANNER_HEIGHT));
			
			// draw the bag
			Image img = null;
			if(suspiciousBag != null) {
				if(suspiciousBag.isClean()) {
					img = cleanImg;
				} else {
					img = susImg;
				}
				g2.drawString(String.valueOf(suspiciousBag.toString()), SEG3_X
						- SCANNER_WIDTH / 2 + 10, 10);
				g2.drawImage(img, SEG3_X - SCANNER_WIDTH / 2,
						BELT_HEIGHT / 2, null);
			}
		}

		public void setSuspiciousBag(Bag bag) {
			this.suspiciousBag = bag;
		}

	}
}
