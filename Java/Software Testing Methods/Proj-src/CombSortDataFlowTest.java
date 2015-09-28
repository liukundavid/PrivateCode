import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;


public class CombSortDataFlowTest {
	CombSort sortAlgorithm = new CombSort();
	
	/**
	 * test1 covers table3 ABCDEFGHJGCK
	 */
	@Test
	public void testCombSort1() throws Throwable {
		int[] input = {-1, 1, 0};
		int[] expect = {-1,0,1};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test2 covers table3  ABCK
	 */
	@Test
	public void testCombSort2() throws Throwable {
		int[] input = {};
		int[] expect = {};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test3 covers table3 ABCDFGCK
	 */
	@Test
	public void testCombSort3() throws Throwable {
		int[] input = {-1};
		int[] expect = {-1};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test4 covers table3 ABCDEFGHIJGCK
	 */
	@Test
	public void testCombSort4() throws Throwable {
		int[] input = {1,-3,-1,0,-4,-2};
		int[] expect = {-4,-3,-2,-1,0,1};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}

}
