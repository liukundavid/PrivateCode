/**
 * @see <a href="http://en.wikipedia.org/wiki/Shellsort">source link</a>
 */
public class ShellSort implements SortingAlgorithm 
{	
	public void sort(int[] arr)
	{
		int h = 1;
		while (h < arr.length/3)
		{
			h = h*3 + 1;
		}
		
		for (; h >= 1; h /= 3)
		{
			for (int k = 0; k < h; k++)
			{
				for (int i = h+k; i < arr.length; i+=h)
				{
					for (int j = i; j >= h && arr[j]<arr[j-h]; j-=h)
					{
						Utility.swap(arr, j, j-h);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) 
	{
		int[] arr = new int[] {50, 42, 12, 79, 32, 65, 21, 41, 56, 12};
		Utility.printArray(arr);
		new ShellSort().sort(arr);
		Utility.printArray(arr);
	}
}
