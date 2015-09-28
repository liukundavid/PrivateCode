
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class BUGFreeCheck
{

	private static final Random RDN = new Random();
	private static int maxArrayScale_ =  100;
	private int[]	initArr;
	private int[]	randomArr;
	
	@Before
	public void setUp() throws Exception
	{
		//generate an array
		//random array scale maximize is maxArrayScale_
		int arrayScale = RDN.nextInt(maxArrayScale_);
		initArr=new int[arrayScale];
		//generate the first item
		int firstInt =RDN.nextInt(65536)-32768;
		initArr[0]=firstInt;
		//ascend create array item
		for(int i=0;i<arrayScale-1;i++)
		{
			initArr[i+1]=initArr[i]+RDN.nextInt(Math.round((65536-firstInt)/maxArrayScale_));
		}
		Utility.printArray(initArr);
		randomArr = initArr.clone();
		//loop 1000 times to out-order array
		for(int j=0;j<1000;j++)
		{
			int firstIndex = RDN.nextInt(arrayScale);
			int secondIdex = RDN.nextInt(arrayScale);
			Utility.swap(randomArr, firstIndex, secondIdex);
		}
		Utility.printArray(randomArr);
	}

	@Test
	public void testQuickSort() throws Throwable
	{
		QuickSort sortAlgorithm= new QuickSort();
		sortAlgorithm.sort(randomArr);
		Utility.printArray(randomArr);
		//the following three lines show how to remove Negative items, similarly we can also use removePositive  
		initArr=Utility.removeNegative(initArr);
		randomArr=Utility.removeNegative(randomArr);
		Utility.printArray(initArr);
		Utility.printArray(randomArr);
		assertTrue(Arrays.equals(initArr, randomArr));
	}
	
	@Test
	public void testMergeSort() throws Throwable
	{
		MergeSort sortAlgorithm= new MergeSort();
		sortAlgorithm.sort(randomArr);
		Utility.printArray(randomArr);
		//the following three lines show how to remove Negative items, similarly we can also use removePositive  
		initArr=Utility.removeNegative(initArr);
		randomArr=Utility.removeNegative(randomArr);
		Utility.printArray(initArr);
		Utility.printArray(randomArr);
		assertTrue(Arrays.equals(initArr, randomArr));
	}
	@Test
	public void testShellSort() throws Throwable
	{
		ShellSort sortAlgorithm= new ShellSort();
		sortAlgorithm.sort(randomArr);
		Utility.printArray(randomArr);
		//the following three lines show how to remove Negative items, similarly we can also use removePositive  
		initArr=Utility.removeNegative(initArr);
		randomArr=Utility.removeNegative(randomArr);
		Utility.printArray(initArr);
		Utility.printArray(randomArr);
		assertTrue(Arrays.equals(initArr, randomArr));
	}
	@Test
	public void testCombSort() throws Throwable
	{
		CombSort sortAlgorithm= new CombSort();
		sortAlgorithm.sort(randomArr);
		Utility.printArray(randomArr);
		//the following three lines show how to remove Negative items, similarly we can also use removePositive  
		initArr=Utility.removeNegative(initArr);
		randomArr=Utility.removeNegative(randomArr);
		Utility.printArray(initArr);
		Utility.printArray(randomArr);
		assertTrue(Arrays.equals(initArr, randomArr));
	}
}
