import java.util.LinkedList;

abstract class PriorityQueue<T extends Comparable<T>> extends Queue<T>{
  public abstract void add(T data);
  public PriorityQueue(){
    super();
  }
}
