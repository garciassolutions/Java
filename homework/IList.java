public interface IList<E>
{
	boolean add(E entry);
	boolean add(int pos, E entry);
	E remove(int pos);
	void clear();
	int size();
	void display(String message);
}