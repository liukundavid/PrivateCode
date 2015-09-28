import java.util.Arrays;

public class Utility {
	
	static public void swap(int[] arr, int index1, int index2)
	{
		if (index1 != index2)
		{
			arr[index1] ^= arr[index2];
			arr[index2] ^= arr[index1];
			arr[index1] ^= arr[index2];
		}
	}
	
	static void printArray(int[] arr)
	{
		for (int i : arr)
		{
			System.out.printf("%d ", i);
		}
		System.out.println();
	}
	
	static boolean equal(int[] arr1, int[] arr2)
	{
		if (arr1.length != arr2.length)
		{
			return false;
		}
		for (int i = 0; i < arr1.length; i++)
		{
			if (arr1[i] != arr2[i])
			{
				return false;
			}
		}
		return true;
	}
	
	private static int[] addElement(int[] org, int added) {
		if(org!=null)
		{
	    int[] result = Arrays.copyOf(org, org.length +1);
	    result[org.length] = added;
	    return result;
		}
		else {
			int[] result = {added};
			return result;
		}
		
	}
	
	public static int[] removeNegative(int[] arr)
	{
		int removedArr[] = null;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]>0)
			{
				removedArr=addElement(removedArr,arr[i]);
			}
		}
		return removedArr;
	}
	
	public static int[] removePositive(int[] arr)
	{
		int removedArr[] = null;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]<0)
			{
				removedArr=addElement(removedArr,arr[i]);
			}
		}
		return removedArr;
	}
}
