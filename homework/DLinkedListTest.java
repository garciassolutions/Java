public class DLinkedListTest
{
	public static void main(String[] args)
	{
		DLinkedList<String> list = new DLinkedList<String>();
		list.display("Initially");
		list.display("Add at end: "  + list.add("zero"));
		list.display("Add at end: "  + list.add("one"));
		list.display("Add at end: "  + list.add("three"));
		list.display("Add at 2: "    + list.add(2, "two"));
		list.display("Add at 5: "    + list.add(5, "five"));
		list.display("Remove at 2: " + list.remove(2));
		list.display("Remove at 0: " + list.remove(0));
		list.display("Add at 0: "    + list.add(0, "zero"));
		list.display("Remove at 1: " + list.remove(1));
		list.display("Remove at 1: " + list.remove(1));
		list.display("Remove at 0: " + list.remove(0));
		list.display("Remove at 0: " + list.remove(0));
		System.out.println("size = " + list.size());
	}
}
