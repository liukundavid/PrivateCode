/**
 * @see <a href="http://en.wikipedia.org/wiki/Quicksort">source link</a>
 */
//import java.util.Random;

public class QuickSort implements SortingAlgorithm 
{
//	public static final Random RND = new Random();
	
	public void sort(int[] arr)
	{
		qsort(arr, 0, arr.length-2);
	}
	
	private static int partition(int[] arr, int begin, int end)
	{
//		int index = begin + RND.nextInt(end - begin + 1);
		int index = (begin + end)/2;
		int pivot = arr[index];
		Utility.swap(arr, index, end);
		for (int i = index = begin; i < end; ++i)
		{
			if (arr[i] < pivot)
			{
				Utility.swap(arr, index++, i);
			}
		}
		Utility.swap(arr, index, end);
		return index;
	}
	
	private static void qsort(int[] arr, int begin, int end)
	{
		if (end > begin)
		{
			int index = partition(arr, begin, end);
//			qsort(arr, begin, index-2);
			qsort(arr, begin, index-1);
			qsort(arr, index+1, end);
		}
	}
	
	public static void main(String[] args) 
	{
		int[] arr = new int[] {1, 3, 5, 7, 9, 2, 4, 6, 8, 1, 3, 5, 7, 9, 2, 4, 6, 8, 1, 3, 5, 7, 9, 2, 4, 6, 8};
		Utility.printArray(arr);
		new QuickSort().sort(arr);
		Utility.printArray(arr);
	}
}
