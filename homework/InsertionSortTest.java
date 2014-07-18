import java.util.Random;
import java.util.Arrays;

public class InsertionSortTest{
	public static final int N = 100;
	public static void main(String[] args){
		InsertionSort<Integer> insertionSort;
		Random rand = new Random();
		Integer[] array1 = new Integer[N], array2;
		//	Sorting a random array of Integers (average case).
		//	Reset counters by constructing a new instance.
		insertionSort = new InsertionSort<Integer>();
		//	Generate the souce array and a copy of the source array.
		for (int i = 0; i < N; i++) {
			array1[i] = rand.nextInt(N);
		}
		array2 = Arrays.copyOf(array1, N);

		//	Display the source array.
		System.out.println(Arrays.asList(array1));

		//	Iterative sort and display results.
		insertionSort.sortIter(array1, N);
		System.out.println(Arrays.asList(array1));
		System.out.printf("  comp: %d, swap: %d, move: %d%n",
			insortionSort.smi.getNComp(),
			insortionSort.smi.getNSwap(),
			insortionSort.smi.getNMove());

		//	Recursive sort and display results.
		insertionSort.sortRec(array2, 0);
		System.out.println(Arrays.asList(array2));
		System.out.printf("  comp: %d, swap: %d, move: %d%n%n",
			insertionSort.smr.getNComp(),
			insertionSort.smr.getNSwap(),
			insertionSort.smr.getNMove());
		//	Sorting an ascending array of Integers (best case).
		//	Reset counters by constructing a new instance.

		//	Generate the souce array and a copy of the source array.
		for (int i=0; i<N; i++) array1[i] = i;
		array2 = Arrays.copyOf(array1, N);

		//	Display the source array.
		System.out.println(Arrays.asList(array1));
		//	Iterative sort and display results.
		insertionSort.sortIter(array1, N);
		System.out.println(Arrays.asList(array1));
		System.out.printf("  comp: %d, swap: %d, move: %d%n",
			insertionSort.smi.getNComp(),
			insertionSort.smi.getNSwap(),
			insertionSort.smi.getNMove());

		//	Recursive sort and display results.
		insertionSort.sortRec(array2, 0);
		System.out.println(Arrays.asList(array2));
		System.out.printf("  comp: %d, swap: %d, move: %d%n%n",
			insertionSort.smr.getNComp(),
			insertionSort.smr.getNSwap(),
			insertionSort.smr.getNMove());

	//	Sorting a descending array of Integers (worst case).

		//	Reset counters by constructing a new instance.
		insertionSort = new InsertionSort<Integer>();

		//	Generate the souce array and a copy of the source array.
		for (int i=0; i<N; i++) array1[i] = N - i;
		array2 = Arrays.copyOf(array1, N);

		//	Display the source array.
		System.out.println(Arrays.asList(array1));

		//	Iterative sort and display results.
		insertionSort.sortIter(array1, N);
		System.out.println(Arrays.asList(array1));
		System.out.printf("  comp: %d, swap: %d, move: %d%n",
			insertionSort.smi.getNComp(),
			insertionSort.smi.getNSwap(),
			insertionSort.smi.getNMove());

		//	Recursive sort and display results.
		insertionSort.sortRec(array2, 0);
		System.out.println(Arrays.asList(array2));
		System.out.printf("  comp: %d, swap: %d, move: %d%n%n",
			insertionSort.smr.getNComp(),
			insertionSort.smr.getNSwap(),
			insertionSort.smr.getNMove());
	}
}
