import java.util.LinkedList;

public class Queue<T>
{
	protected LinkedList<T> list;

	public Queue()
	{
		list = new LinkedList<T>();
	}

	public boolean isEmpty()    { return this.list.isEmpty();  }
	public int     getSize()    { return this.list.size();     }
	public T       get()        { return this.list.getFirst(); }
	public T       remove()     { return this.list.remove();   }
	public void    add(T entry) { this.list.add(entry);        }
}