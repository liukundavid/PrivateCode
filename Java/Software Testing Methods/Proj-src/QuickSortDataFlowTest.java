import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;



public class QuickSortDataFlowTest
{
	QuickSort sortAlgorithm = new QuickSort();
	
	/**
	 * test1 covers CDEFGDZ,HIJKLMNOPMQRY
	 */
	@Test
	public void testShellSort1() throws Throwable
	{
		int[] input = new int[] {2,5,6,1,9,6};
		int[] expect = {1,2,5,6,6,9};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test2 covers HIJKLMQRY
	 */
	@Test
	public void testShellSort2() throws Throwable
	{
		int[] input = new int[] {2};
		int[] expect = {2};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test3 covers HIJKLMNPMQRY
	 */
	@Test
	public void testShellSort3() throws Throwable
	{
		int[] input = new int[] {-1,2,0};
		int[] expect = {-1,0,2};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	

	
	
}
