public class Event implements Comparable<Event>{
  public static final int NEW_REQUEST = 1;
  public static final int SEEK_SATISFIED = 2;
  public static final int ROT_SATISFIED = 3;
  public static final int REQUEST_SATISFIED = 4;
  
  private int type;
  private long time;
  
  public int getType(){ // Accessor method
    return this.type;
  }
  public long getTime(){ // Accessor method
    return this.time;
  }
  public Event(){
    // Blank...
  }
  public Event(int t, long u){ // Complete constructor
    type = t;
    time = u;
  }
  public int compareTo(Event obj){
    return (int)(this.time - obj.time);
  }
  public boolean equals(Event other){
    return this.time == other.time;
  }
  public String toString(){
  StringBuilder S = new StringBuilder();
    if(this.type < 0 || this.type > 3) return "undefined: " + this.type + " @ " + Fmt.time(this.time) + "\n";
    else
      switch(this.type){
        case 1:{
          S.append("[+] NEW_REQUEST:");
          break;
        }
        case 2:{
          S.append("[-] SEEK_SATISFIED:");
          break;
        }
        case 3:{
          S.append("[*] ROT_SATISFIED:");
          break;
        }
        case 4:{
          S.append("[^] REQUEST_SATISFIED:");
          break;
        }
      }
    S.append(" @ " + Fmt.time(time));
    return S.toString();
  }
}
