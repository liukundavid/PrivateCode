import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class ShellSortDataFlowTest
{
	
	ShellSort sortAlgorithm = new ShellSort();
	
	/**
	 * test1 covers ABC(DC)m(EFG(HI(JK(L(M)0-1NK)nOI)pPG)qQ)rER
	 */
	@Test
	public void testShellSort1() throws Throwable
	{
		int[] input = new int[] {50, 42, 12, 79, 32, 65, 21, 41, 7, 56, 12, 24, 78, 40};
		int[] expect = {7, 12, 12, 21, 24, 32, 40, 41, 42, 50, 56, 65, 78, 79};
		sortAlgorithm.sort(input);
		assertTrue(Arrays.equals(input, expect));
	}

}
