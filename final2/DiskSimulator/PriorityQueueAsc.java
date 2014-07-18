import java.util.ListIterator;

public class PriorityQueueAsc<T extends Comparable<T>> extends PriorityQueue<T>
{
	public PriorityQueueAsc()
	{
		super();
	}

	public void add(T data)
	{
		if (this.isEmpty())
		{
			//if there's nothing in our list.. just add it
			this.list.add(data);
		}
		else if (this.list.getFirst().compareTo(data) >= 0)
		{
			//if count != 0 and data is of lower priority that the first element, add it there
			this.list.addFirst(data);
		}
		else if (this.list.getLast().compareTo(data) <= 0)
		{
			//if the count != 0 and the data we're adding is bigger than the last element,
			//add it at the end.
			this.list.add(data);
		}
		else
		{
			//if the count != 0 and the data is smaller than the last element, but bigger
			//than the first element, then it belongs in the middle somewhere
			ListIterator<T> it = this.list.listIterator(0);
			T tempT;
			boolean keepGoing = true;
			while (keepGoing)
			{
				tempT = it.next();
				if (tempT.compareTo(data) >= 0)
				{
					//if the next element is bigger than data, data goes before this element
					this.list.add(it.previousIndex(), data);
					keepGoing = false;
				}
			}
		}
	}
}