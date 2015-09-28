/**
 * @see <a href="http://en.wikipedia.org/wiki/Comb_sort">source link</a>
 */
public class CombSortBug3 implements SortingAlgorithm
{
	public void sort(int[] arr)
	{
		int gap = arr.length;
	    boolean swapped = true;
	    while (gap > 1 || swapped)
	    {
	        if (gap > 1) 
	        {
	            gap = (int) (gap / 1.3);
//	        	bug1:gap = (int) (gap / 3);
	        }
	        int i = 0;
//	        bug2:int i = 1;
	        swapped = false;
	        while (i + gap < arr.length) 
	        {
	        	if (arr[i] > arr[i+gap])
	        	{
	        		Utility.swap(arr, i, i+gap);
	                swapped = true;
	            }
	            //original:i++;
	            ++i;
	        }
	    }
	}
	
	public static void main(String[] args) 
	{
		int[] arr = new int[] {1, 3, 5, 7, 9, 2, 4, 6, 8};
		Utility.printArray(arr);
		new CombSortBug3().sort(arr);
		Utility.printArray(arr);
	}
}