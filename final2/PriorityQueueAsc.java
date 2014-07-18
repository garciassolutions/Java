import java.util.*;

class PriorityQueueAsc<T extends Comparable <T>> extends PriorityQueue<T>{
  public PriorityQueueAsc(){
    super();
  }
  public void add(T data){
    if(this.stuff.isEmpty()) stuff.add(data);
    else if(this.stuff.getFirst().compareTo(data) >= 0) this.stuff.addFirst(data);
    else if(this.stuff.getLast().compareTo(data) <= 0) this.stuff.add(data);
    else{
      ListIterator<T> litr = stuff.listIterator(0);
      T tmp;
      boolean flag = false;
      while(!flag){
        tmp = litr.next(); // Does this only get element 2?
        if(tmp.compareTo(data) >= 0){ // When data is less then an element, back up and add it.
          this.stuff.add(litr.previousIndex(), data);
          flag = true;
        }
      }
    }
  }
}
