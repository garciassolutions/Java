/*
 * Linked List Class
 * Written by Anthony Garcia
 * CST-246-100 - Data Structures
 */
public class DLinkedList<E>{
  private class node{
    E data;
    node next;
  }
  private node head;
  private int size = 0;
  public DLinkedList(){
    head = new node(); // Could say head = null, and then try to add it. But if we cannot allocate space for one object, we should throw an exception before we try to go ANY farther.
    head.next = null;
    head.data = null;
  }
  public boolean add(E entry){
    if(this.size == 0){
      this.head.data = entry;
    }
    else{
      node tmp, tmp2;
      tmp = this.head;
      while(tmp.next != null) tmp = tmp.next; // Find the end of the list.
      tmp2 = new node();
      tmp2.data = entry;
      tmp2.next = null;
      tmp.next = tmp2;
    }
    this.size++; // Increase the size
    return true;
  }
  public boolean add(int pos, E entry){
    node tmp, tmp2;
    if(pos > this.size+1 || pos < 0) return false;
    else if(pos == this.size+1) add(entry);
    else{
      tmp = head;
      for(int i=0;i<pos;i++) tmp = tmp.next;
      tmp2 = new node();
      tmp2.data = entry; // Set our new object.
      tmp2.next = tmp.next; // Point our list onward.
      tmp.next = tmp2; // Add our new item to the list.
      this.size++;
    }
    return true;
  }
  public int size(){
    return this.size;
  }
  void clear(){
    this.head=null;
    this.size=0;
  }
  void display(String message){ // Whats this do...?
    System.out.println(message);
  }
  E remove(int pos){
    if(pos > this.size || pos < 0 || this.size == 0) return null;
    node tmp, tmp2 = null;
    tmp = this.head;
    if(pos == 0){
      if(this.head.next != null) this.head = this.head.next;
    }
    else{
      for(int i=0;i<pos;i++, tmp=tmp.next) tmp2=tmp;
      if(tmp.next != null) tmp2.next = tmp.next; // Remove tmp from the list.
      tmp.next = null; // Don't let it point to anything for garbage collection?
    }
    this.size--; // Decrement the size.
    return tmp.data;
  }
}
