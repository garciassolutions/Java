import java.util.LinkedList;

abstract class PriorityQueue<T extends Comparable<T>>{
  public abstract void add(T data);
  protected class Node<T>{
    T data;
    Node<T> next;
  }
  Node head, tail;
  private int count;
  public PriorityQueue(){
    this.head = new Node<T>();
    this.tail = this.head;
    this.count = 0;
  }
  public int getSize(){
    return this.count;
  }
  public boolean isEmpty(){
    return this.count==0;
  }
  public void clear(){
    this.head = null;
    this.tail = this.head;
    this.count = 0;
  }
  public T get(){
    return (T) this.head.data; // Return the first element.
  }
  public T remove(){
    if(this.count == 0) return null;
    else{
      Node<T> t1, t2;
      t1 = this.head;
      t2 = null;
      while(!t1.next.equals(null)){
        t2 = t1;
        t1 = t1.next;
      }
      if(!t2.equals(null)){ // There was more then one element.
        t2.next = null;
        this.tail = t2;
      }
      else{ // 
        this.head = null;
        this.tail = null;
      }
      this.count--;
      return t1.data;
    }
  }
}
