/**
 * @author Kun Liu
 *
 * @Purpose This class is to define all the constants which other classes would
 *          use, including all parameters of all the test cases
 */
public class Constants {

	// Define the map length
	public static final int MAP_LENGTH = 30;

	// Define the life span of daisies
	public static final int Lifespan = 25;

	// Define how much of the temperature amount of a patch is diffused
	// between neighboring surface patches
	public static final double TemperatureDiffuse = 0.5;

	// Define Constant data for Luminosity of 1.00
	public static final double constantLuminosity = 1.00;
	public static final int whitePopularityForConstantLuminosity = 25;
	public static final int blackPopularityForConstantLuminosity = 25;
	public static final double whiteAlbedoForConstantLuminosity = 0.75;
	public static final double blackAlbedoForConstantLuminosity = 0.25;
	public static final double surfaceAlbedoForConstantLuminosity = 0.70;
	// Set the Group 1 settings
	public static final double extremelyLowLuminosityForGroup1 = 0.5;
	public static final double lowLuminosityForGroup1 = 0.7;
	public static final double highLuminosityForGroup1 = 1.5;
	public static final double extremelyHighLuminosityForGroup1 = 2.5;
	
	// Set the Group 2 settings
	public static final double lowSurfaceAlbedoforGroup2 = 0;
	public static final double highSurfaceAlbedoforGroup2 = 1.0;
	
	// Set the Group 3 settings
	public static final double lowWhiteAlbedoforGroup3 = 0.4;
	public static final double highBlackAlbedoforGroup3 = 0.6;
	
	// Set the Group 4 settings
	public static final int largeWhitePopulationforGroup4 = 50;
	public static final int largeBlackPopulationforGroup4 = 50;
}
