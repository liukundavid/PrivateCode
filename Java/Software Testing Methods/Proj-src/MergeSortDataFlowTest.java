import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;



public class MergeSortDataFlowTest
{
	MergeSort sortAlgorithm = new MergeSort();

	/**
	 * test1 covers table2 ABCDEHKNQ
	 */
	@Test
	public void testMergeSort1() throws Throwable
	{
		int[] input = {-3,6};
		int[] expect = {-3,6};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test1 covers table2 ABCDEHKNQ
	 */
	@Test
	public void testMergeSort2() throws Throwable
	{
		int[] input = {6,-3};
		int[] expect = {-3,6};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test1 covers table2 ABCDEHKLMKNQ
	 */
	@Test
	public void testMergeSort3() throws Throwable
	{
		int[] input = {-3,8,6};
		int[] expect = {-3,6,8};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test1 covers table1 ABC
	 */
	@Test
	public void testMergeSort4() throws Throwable
	{
		int[] input = {8};
		int[] expect = {8};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	/**
	 * test1 covers table1 ABCDEFGHIJ
	 */
	@Test
	public void testMergeSort5() throws Throwable
	{
		int[] input = {3,9,-1,3,8};
		int[] expect = {-1,3,3,8,9};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	@Test
	public void testMergeSort6() throws Throwable
	{
		int[] input = {10,9,-1,3,8};
		int[] expect = {-1,3,8,9,10};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}
	
	
}
