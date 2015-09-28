import static org.junit.Assert.*;

import java.util.Arrays;

public class BoundaryValueTest 
{
	SortingAlgorithm[] algorithms = new SortingAlgorithm[] {
		new CombSort(), new MergeSort(), new QuickSort(), new ShellSort()
	};
	
	/**
	 * ArraySize = 0
	 */
	@org.junit.Test
	public void ec1() 
	{
		int[] input = {};
		int[] expect = {};
		verify(input, expect);
	}
	
	/**
	 * ArraySize = 1 && 
	 * contains negative number
	 */
	@org.junit.Test
	public void ec2() 
	{
		int[] input = {-7};
		int[] expect = {-7};
		verify(input, expect);
	}
	
	/**
	 * ArraySize = 1 && 
	 * contains zero
	 */
	@org.junit.Test
	public void ec3() 
	{
		int[] input = {0};
		int[] expect = {0};
		verify(input, expect);
	}
	
	/**
	 * ArraySize = 1 &&
	 * contains positive number
	 */
	@org.junit.Test
	public void ec4() 
	{
		int[] input = {7};
		int[] expect = {7};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain negative number && 
	 * contains zero && 
	 * contains duplicated number
	 */
	@org.junit.Test
	public void ec5() 
	{
		int[] input = {2, 0, 2};
		int[] expect = {0, 2, 2};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain negative number && 
	 * contains zero && 
	 * does not contain duplicated number
	 */
	@org.junit.Test
	public void ec6() 
	{
		int[] input = {2, 0};
		int[] expect = {0, 2};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain negative number && 
	 * does not contain zero && 
	 * contains duplicated number
	 */
	@org.junit.Test
	public void ec7() 
	{
		int[] input = {2, 2, 1};
		int[] expect = {1, 2, 2};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain negative number && 
	 * does not contain zero && 
	 * does not contain duplicated number
	 */
	@org.junit.Test
	public void ec8() 
	{
		int[] input = {8, 4, 1};
		int[] expect = {1, 4, 8};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain positive number && 
	 * contains zero && 
	 * contains duplicated number
	 */
	@org.junit.Test
	public void ec9() 
	{
		int[] input = {-2, 0, -2};
		int[] expect = {-2, -2, 0};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain positive number && 
	 * contains zero && 
	 * does not contain duplicated number
	 */
	@org.junit.Test
	public void ec10() 
	{
		int[] input = {-2, 0};
		int[] expect = {-2, 0};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain positive number && 
	 * does not contains zero && 
	 * contains duplicated number
	 */
	@org.junit.Test
	public void ec11() 
	{
		int[] input = {-2, -1, -2};
		int[] expect = {-2, -2, -1};
		verify(input, expect);
	}
	
	/**
	 * ArraySize > 1 && 
	 * does not contain positive number && 
	 * does not contains zero && 
	 * does not contain duplicated number
	 */
	@org.junit.Test
	public void ec12() 
	{
		int[] input = {-4, -8, -1};
		int[] expect = {-8, -4, -1};
		verify(input, expect);
	}
	
	/*
	 * Tools
	 */
	private void verify(int[] input, int[] expect)
	{
		boolean[] result = new boolean[algorithms.length];
		for (int i = 0; i < algorithms.length; i++)
		{
			int[] output = new int[input.length]; 
			System.arraycopy(input, 0, output, 0, input.length);
			algorithms[i].sort(output); 
			//result[i] = Utility.equal(output, expect);
			result[i]=Arrays.equals(output, expect);
		}
		checkResult(result);
	}
	
	private void checkResult(boolean[] result)
	{
		String failed = "";
		for (int i = 0; i < algorithms.length; i++)
		{
			if (!result[i])
			{
				failed += algorithms[i].getClass().getName() + " ";
			}
		}
		assertTrue(failed, allTrue(result));
	}
	
	private boolean allTrue(boolean[] result)
	{
		boolean ret = true;
		for (boolean b : result)
		{
			ret &= b;
		}
		return ret;
	}
}
