public class SortMetrics<T extends Comparable<T>>
{
	//	Keep track of the number of compares, swaps, and moves.
	private int nComp;
	private int nSwap;
	private int nMove;

	//	Compare the ith and jth elements of array a.
	//	Increment the compare count for each call.
	public int comp(T[] a, int i, int j)
	{
		this.incrNComp();
		return a[i].compareTo(a[j]);
	}

	//	Swap the ith and jth elements of array a.
	//	Increment the swap count for each call only when a swap is performed.
	public void swap(T[] a, int i, int j)
	{
		if (i != j) {
			this.incrNSwap();
			T t = a[i];
			a[i] = a[j];
			a[j] = t;
		}
	}

	//	Move the ith element of array a to the jth position of array a.
	//	Increment the move count for each call only when a move is performed.
	public void move(T[] a, int i, int j)
	{
		if (i != j) {
			this.incrNMove();
			a[j] = a[i];
		}
	}

	//	Increment the counts.
	//	This can be use if data is accessed outside of the array.
	public void incrNComp() { this.nComp++; }
	public void incrNSwap() { this.nSwap++; }
	public void incrNMove() { this.nMove++; }

	//	Access the counts.
	public int getNComp() { return this.nComp; }
	public int getNSwap() { return this.nSwap; }
	public int getNMove() { return this.nMove; }
}
