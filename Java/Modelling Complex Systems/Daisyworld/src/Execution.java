import java.lang.Math;
import java.util.ArrayList;

/**
 * @author Kun Liu
 *
 * @Purpose This class is to run the test cases. Most logics would be done here.
 */
public class Execution {
	private int numOfWhites;
	private int numOfBlacks;
	private double whiteAlbedo;
	private double blackAlbedo;
	private double surfaceAlbedo;
	private double solarLuminosity;
	private Patch[][] daisiesMap;
	private int tick;

	/**
	 * Constructor
	 * 
	 * @param _whites
	 *            ratio of white daisies
	 * @param _blacks
	 *            ratio of black daisies
	 * @param _whiteAlbedo
	 * @param _blackAlbedo
	 * @param _surfaceAlbedo
	 * @param _luminosity
	 */
	public Execution(int _whites, int _blacks, double _whiteAlbedo,
			double _blackAlbedo, double _surfaceAlbedo, double _luminosity) {
		this.numOfWhites = _whites * Constants.MAP_LENGTH
				* Constants.MAP_LENGTH / 100;
		this.numOfBlacks = _blacks * Constants.MAP_LENGTH
				* Constants.MAP_LENGTH / 100;
		this.whiteAlbedo = _whiteAlbedo;
		this.blackAlbedo = _blackAlbedo;
		this.surfaceAlbedo = _surfaceAlbedo;
		this.solarLuminosity = _luminosity;
		this.tick = 0;
		this.daisiesMap = new Patch[Constants.MAP_LENGTH][Constants.MAP_LENGTH];
	}

	/**
	 * randomly seed white daisies and black daisies
	 */
	private void seed_Daisies_Randomly() {
		// Open Ground would be represented as 0
		// initialize all the patches as open ground
		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				daisiesMap[i][j] = new Patch(this.surfaceAlbedo,
						TurtleType.OpenGround);
			}
		}

		// seed whites as 1, replace the open ground with white daisy
		for (int i = 0; i < this.numOfWhites;) {
			int xIndex = (int) (Math.random() * Constants.MAP_LENGTH);
			int yIndex = (int) (Math.random() * Constants.MAP_LENGTH);
			if (!(daisiesMap[xIndex][yIndex].turtle instanceof Daisy)) {
				daisiesMap[xIndex][yIndex] = new Patch(this.whiteAlbedo,
						TurtleType.WhiteDaisy);
				i++;
			}
		}

		// seed blacks as 2
		for (int j = 0; j < this.numOfBlacks;) {
			int xIndex = (int) (Math.random() * Constants.MAP_LENGTH);
			int yIndex = (int) (Math.random() * Constants.MAP_LENGTH);
			if (!(daisiesMap[xIndex][yIndex].turtle instanceof Daisy)) {
				daisiesMap[xIndex][yIndex] = new Patch(this.blackAlbedo,
						TurtleType.BlackDaisy);
				j++;
			}
		}
	}

	/**
	 * using some digits to draw the daisy matrix 0 represents open gournd 1
	 * stands for white daisy and 2 stands for black daisy
	 */
	private void printDaisiesMap() {
		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				System.out.print(this.daisiesMap[i][j].turtle.type + " ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * print age of all the daisies, using 0 to represent surface
	 */
	private void printDaisiesAge() {
		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {

				if (daisiesMap[i][j].turtle instanceof Daisy) {
					Daisy daisy = (Daisy) daisiesMap[i][j].turtle;
					System.out.print(daisy.age + " ");
				} else {
					System.out.print("0 ");
				}

			}
			System.out.print("\n");
		}
	}

	/**
	 * iterate all the patches and count whites and blacks
	 */
	private void printNumberOfWhitesAndBlacks() {
		int whites = 0;
		int blacks = 0;

		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				if (daisiesMap[i][j].turtle instanceof Daisy) {
					Daisy daisy = (Daisy) daisiesMap[i][j].turtle;
					if (TurtleType.WhiteDaisy == daisy.toTurtleType()) {
						whites++;
					} else if (TurtleType.BlackDaisy == daisy.toTurtleType()) {
						blacks++;
					} else {

					}
				}
			}
		}

		System.out.println(whites + "\t" + blacks);
	}

	/**
	 * iterate all the patches and calculate their temperature
	 */
	private void calculate_allPatches_temperature() {
		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				this.daisiesMap[i][j]
						.calculate_temperature(this.solarLuminosity);
				// System.out.print(this.daisiesMap[i][j].current_temperature
				// + " ");
			}
		}
	}

	/**
	 * calculate the temperature that the current patch diffuse to its neighbors
	 */
	private void diffuseTemperature() {
		double[][] origin_temperature = new double[Constants.MAP_LENGTH][Constants.MAP_LENGTH];
		double[][] diffused_temperature = new double[Constants.MAP_LENGTH][Constants.MAP_LENGTH];

		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				int count = 0;
				double temperature = daisiesMap[i][j].current_temperature;

				// judge the validity of all the possible neighbors
				// and count the validated neighbors
				if (i - 1 >= 0 && j - 1 >= 0) {
					diffused_temperature[i - 1][j - 1] += (0.5 * temperature / 8);
					count += 1;
				}

				if (i - 1 >= 0 && j + 1 < Constants.MAP_LENGTH) {
					diffused_temperature[i - 1][j + 1] += (0.5 * temperature / 8);
					count += 1;
				}

				if (i + 1 < Constants.MAP_LENGTH && j - 1 >= 0) {
					diffused_temperature[i + 1][j - 1] += (0.5 * temperature / 8);
					count += 1;
				}

				if (i + 1 < Constants.MAP_LENGTH
						&& j + 1 < Constants.MAP_LENGTH) {
					diffused_temperature[i + 1][j + 1] += (0.5 * temperature / 8);
					count += 1;
				}

				if (i - 1 >= 0 && j >= 0) {
					diffused_temperature[i - 1][j] += (0.5 * temperature / 8);
					count += 1;
				}

				if (j - 1 >= 0 && i >= 0) {
					diffused_temperature[i][j - 1] += (0.5 * temperature / 8);
					count += 1;
				}

				if (i + 1 < Constants.MAP_LENGTH && j >= 0) {
					diffused_temperature[i + 1][j] += (0.5 * temperature / 8);
					count += 1;
				}

				if (j + 1 < Constants.MAP_LENGTH && i >= 0) {
					diffused_temperature[i][j + 1] += (0.5 * temperature / 8);
					count += 1;
				}

				// the real temperature should minus the temperature it diffuses
				// to its neighbors
				origin_temperature[i][j] = daisiesMap[i][j].current_temperature
						* (1 - 0.5 * (count / 8));
				daisiesMap[i][j].numOfNeighbour = count;
			}
		}

		// the real temperature should plus the temperature its neighbors
		// diffuse.
		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				daisiesMap[i][j].current_temperature = origin_temperature[i][j]
						+ diffused_temperature[i][j];
			}
		}
	}

	/**
	 * calculate the threshold of seeding and find a open ground to seed new
	 * daisy and clear the daisies that the age is more than its lifespan
	 */
	private void checkSurvivability() {
		// all the existing daisies' age + 1
		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				if (daisiesMap[i][j].turtle instanceof Daisy) {

					Daisy daisy = (Daisy) daisiesMap[i][j].turtle;
					Patch seed_place = daisiesMap[i][j];
					// if the age of current daisy is less than its lifespan
					// then it is possible to seed, otherwise, reset the current
					// patch.
					if (daisy.age < Constants.Lifespan) {
						double local_temperature = seed_place.current_temperature;

						// This equation may look complex, but it is just a
						// parabola.
						// This parabola has a peak value of 1 -- the
						// maximum
						// growth factor possible at an optimum
						// temperature of 22.5 degrees C
						// -- and drops to zero at local temperatures of 5
						// degrees C and 40 degrees C. [the x-intercepts]
						// Thus, growth of new daisies can only occur within
						// this temperature range,
						// with decreasing probability of growth new daisies
						// closer to the x-intercepts of the parabolas
						// remember, however, that this probability
						// calculation
						// is based on the local temperature.

						double seed_threshold = ((0.1457 * local_temperature)
								- (0.0032 * (local_temperature * local_temperature)) - (0.6443));

						if (Math.random() < seed_threshold) {
							int xIndex = 0;
							int yIndex = 0;

							// choose a right place to seed
							// use a 3*3 matrix to represent the current patch
							// and its neighbours. the centre one stands for
							// current patch
							if (i - 1 >= 0
									&& j - 1 >= 0
									&& !(daisiesMap[i - 1][j - 1].turtle instanceof Daisy)) {
								xIndex = i - 1;
								yIndex = j - 1;
							} else if (i - 1 >= 0
									&& j >= 0
									&& !(daisiesMap[i - 1][j].turtle instanceof Daisy)) {
								xIndex = i - 1;
								yIndex = j;
							} else if (i - 1 >= 0
									&& j + 1 < Constants.MAP_LENGTH
									&& !(daisiesMap[i - 1][j + 1].turtle instanceof Daisy)) {
								xIndex = i - 1;
								yIndex = j + 1;
							} else if (i >= 0
									&& j - 1 >= 0
									&& !(daisiesMap[i][j - 1].turtle instanceof Daisy)) {
								xIndex = i;
								yIndex = j - 1;
							} else if (i >= 0
									&& j + 1 < Constants.MAP_LENGTH
									&& !(daisiesMap[i][j + 1].turtle instanceof Daisy)) {
								xIndex = i;
								yIndex = j + 1;
							} else if (i + 1 < Constants.MAP_LENGTH
									&& j - 1 >= 0
									&& !(daisiesMap[i + 1][j - 1].turtle instanceof Daisy)) {
								xIndex = i + 1;
								yIndex = j - 1;
							} else if (i + 1 < Constants.MAP_LENGTH
									&& j >= 0
									&& !(daisiesMap[i + 1][j].turtle instanceof Daisy)) {
								xIndex = i + 1;
								yIndex = j;
							} else if (i + 1 < Constants.MAP_LENGTH
									&& j + 1 < Constants.MAP_LENGTH
									&& !(daisiesMap[i + 1][j + 1].turtle instanceof Daisy)) {
								xIndex = i + 1;
								yIndex = j + 1;
							}

							if (seed_place != null) {
								double albedo = 0.0;
								if (daisy.toTurtleType() == TurtleType.WhiteDaisy) {
									albedo = this.whiteAlbedo;
								} else {
									albedo = this.blackAlbedo;
								}

								// create a new daisy of the same color as the
								// current one
								// and seed it on the right place
								daisiesMap[xIndex][yIndex] = new Patch(albedo,
										daisy.toTurtleType());
								daisiesMap[xIndex][yIndex].current_temperature = local_temperature;
							}
						}
					} else {
						// if age > lifespan then reset current patch
						daisiesMap[i][j] = new Patch(this.surfaceAlbedo,
								TurtleType.OpenGround);
					}
					daisy.age += 1;
				}
			}
		}
		// printDaisiesAge();

	}

	/**
	 * calculate the global temperature
	 */
	private void calculate_global_temperature() {
		double all_temperature = 0.0;

		// the global temperature is the mean value of all the patches'
		// temperature. Iterate all the patches and calculate the mean.
		for (int i = 0; i < Constants.MAP_LENGTH; i++) {
			for (int j = 0; j < Constants.MAP_LENGTH; j++) {
				all_temperature += daisiesMap[i][j].current_temperature;
			}
		}

		// print the global temperature
		// System.out.println(all_temperature
		// / (Constants.MAP_LENGTH * Constants.MAP_LENGTH));
	}

	/**
	 * setup the initial environment
	 */
	public void setupDaisyWorld() {
		// randomly seeding daisies
		seed_Daisies_Randomly();
		// printDaisiesMap();

		// calculate the initial temperature of patches
		calculate_allPatches_temperature();
		// calculate the initial global temperature
		calculate_global_temperature();
	}

	/**
	 * run the DaisyWorld Model
	 */
	public void goDaisyWorld() {
		while (++tick <= 500) {
			// System.out.format("%d's temperature \n", tick);
			calculate_allPatches_temperature();
			diffuseTemperature();
			checkSurvivability();
			calculate_global_temperature();
			printNumberOfWhitesAndBlacks();
		}
	}

	/**
	 * Run the test cases here
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * For all the test cases, the same situation that and for every 25
		 * ticks, the number of white daisies or black daisies would decline
		 * dramatically would occur
		 * 
		 * because large amount of daisies would die of the old age at each 25
		 * ticks
		 */

		// Constant Luminosity
		// the trendency of whites and blacks are the same
		Execution exe1 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.constantLuminosity);

		// Group 1 Extremely Low Luminosity
		Execution exe2 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.extremelyLowLuminosityForGroup1);

		// Group 1 Low Luminosity
		Execution exe3 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.lowLuminosityForGroup1);

		// Group 1 High Luminosity
		Execution exe4 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.highLuminosityForGroup1);

		// Group 1 Extremely High Luminosity
		Execution exe5 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.extremelyHighLuminosityForGroup1);

		// Group 2 Low Surface Albedo
		Execution exe6 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.lowSurfaceAlbedoforGroup2,
				Constants.constantLuminosity);

		// Group 2 High Surface Albedo
		Execution exe7 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.highSurfaceAlbedoforGroup2,
				Constants.constantLuminosity);

		// Group 3 Low White Albedo
		Execution exe8 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.lowWhiteAlbedoforGroup3,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.constantLuminosity);

		// Group 3 High Black Albedo
		Execution exe9 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.highBlackAlbedoforGroup3,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.constantLuminosity);

		// Group 4 Large White Population
		//
		Execution exe10 = new Execution(
				Constants.largeWhitePopulationforGroup4,
				Constants.blackPopularityForConstantLuminosity,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.constantLuminosity);

		// Group 4 Large White Population and Large Black Population
		// The result is quite different from the result of original model
		// Mainly because all the daisies die of the old age in the first
		// generation and no offspring reproduced
		Execution exe11 = new Execution(
				Constants.whitePopularityForConstantLuminosity,
				Constants.largeBlackPopulationforGroup4,
				Constants.whiteAlbedoForConstantLuminosity,
				Constants.blackAlbedoForConstantLuminosity,
				Constants.surfaceAlbedoForConstantLuminosity,
				Constants.constantLuminosity);

		// store the objects into the arraylist
		ArrayList<Execution> exeArray = new ArrayList<Execution>();
		exeArray.add(exe1);
		exeArray.add(exe2);
		exeArray.add(exe3);
		exeArray.add(exe4);
		exeArray.add(exe5);
		exeArray.add(exe6);
		exeArray.add(exe7);
		exeArray.add(exe8);
		exeArray.add(exe9);
		exeArray.add(exe10);
		exeArray.add(exe11);

		// Run all the test cases and print all the outputs with specific
		// parameters
		for (Execution exe : exeArray) {
			System.out.println("\n");
			exe.setupDaisyWorld();
			exe.goDaisyWorld();
			System.out.println("\n\n");
		}

	}
}
