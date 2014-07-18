import java.util.LinkedList;

public class Queue<T>{
  protected LinkedList<T> stuff;
  public Queue(){
    this.stuff = new LinkedList<T>();
  }
  public int getSize(){
    return this.stuff.size();
  }
  public boolean isEmpty(){
    return this.stuff.size()==0;
  }
  public void clear(){
    this.stuff.clear();
  }
  public void add(T data){
    this.stuff.add(data);
  }
  public T get(){
    return this.stuff.getFirst();
  }
  public T remove(){
    return this.stuff.remove();
  }
}
