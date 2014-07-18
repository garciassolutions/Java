import java.util.*;

class PriorityQueueDesc<T extends Comparable<T>> extends PriorityQueue<T>{
  public PriorityQueueDesc(){
    super();
  }
  public void add(T data){
    if(stuff.isEmpty()) stuff.add(data);
    else if(stuff.getFirst().compareTo(data) <= 0) stuff.addFirst(data);
    else if(stuff.getFirst().compareTo(data) >= 0) stuff.add(data);
     else{
      ListIterator<T> litr = stuff.listIterator();
      T tmp;
      while(litr.hasNext()){
        tmp = litr.next();
        if(tmp.compareTo(data) <= 0){
          this.stuff.add(litr.previousIndex(), data);
          break;
        }
      }
    }
  }
}
