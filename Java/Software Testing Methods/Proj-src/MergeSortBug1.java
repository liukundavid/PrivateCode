/**
 * @see <a href="http://en.wikipedia.org/wiki/Merge_sort">source link</a>
 */
public class MergeSortBug1 implements SortingAlgorithm 
{
	public void sort(int[] arr)
	{
		if (arr.length < 1)
//		bug2:if (arr.length == 0)
//		bug3: if (arr.length < 3)
		// original: if (arr.length <= 1)
		{
			return;
		}
		
		int half = arr.length/2;
		int[] arr1 = new int[half];
		int[] arr2 = new int[arr.length - half];
		System.arraycopy(arr, 0, arr1, 0, arr1.length);
		System.arraycopy(arr, half, arr2, 0, arr2.length);
		sort(arr1);
		sort(arr2);
		System.arraycopy(merge(arr1, arr2), 0, arr, 0, arr.length);
	}
	
	private static int[] merge(int[] arr1, int[] arr2)
	{
		int[] result = new int[arr1.length+arr2.length];
		int i = 0;
		int j = 0;
		int k = 0;
		while(true)
		{
			if(arr1[i] < arr2[j])
			{
				result[k] = arr1[i];
				if(++i>arr1.length-1)
				{
					break;
				}
			}
			else
			{
				result[k] = arr2[j];
				if(++j>arr2.length-1)
				{
					break;
				}
			}
			k++;
		}
		for(;i<arr1.length;i++)
		{
			result[++k] = arr1[i];
		}
		for(;j<arr2.length;j++)
		{
			result[++k] = arr2[j];
		}
		return result;
	}
	
	public static void main(String[] args) 
	{
		int[] arr = new int[] {1, 3, 5, 7, 9, 2, 4, 6, 8};
		Utility.printArray(arr);
		new MergeSortBug1().sort(arr);
		Utility.printArray(arr);
	}
}
