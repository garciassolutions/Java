class DiskRequest implements Comparable<DiskRequest>{

  static private int seq;
  private int reqNum, cylinder, head, sector;
  private long requestTime, startTime, satisfyTime;

  public DiskRequest(){
    seq++;
    reqNum = this.seq;
    this.requestTime = this.startTime = this.satisfyTime = this.cylinder = this.head = this.sector = 0;
  }
  public DiskRequest(int cyl, int hdd, int sec, long rt){
    seq++;
    reqNum = this.seq;
    this.cylinder = cyl;
    this.head = hdd;
    this.sector = sec;
    this.requestTime = rt;
    this.startTime = this.satisfyTime = 0;
  }
  public DiskRequest(int block, long p, DiskConfig obj){
    seq++;
    reqNum = seq;
    this.cylinder = block/(obj.getSectors()*obj.getHeads());
    this.head = (block%(obj.getHeads()*obj.getSectors()))/obj.getSectors();
    this.sector = (block%(obj.getHeads()*obj.getSectors()))%obj.getSectors();
  }
  static public void resetSeq(){
    seq = 0;
  }
  public void setStartTime(long s){
    this.startTime = s;
  }
  public long setSatisfyTime(long s){
    this.satisfyTime = s;
    return this.satisfyTime;
  }
  public int getCylinder(){
    return this.cylinder;
  }
  public int getHead(){
    return this.head;
  }
  public int getSector(){
    return this.sector;
  }
  public int getReqNum(){
    return this.reqNum;
  }
  public long getRequestTime(){
    return this.requestTime;
  }
  public long getStartTime(){
    return this.startTime;
  }
  public long getSatisfyTime(){
    return this.satisfyTime;
  }
  public int compareTo(DiskRequest obj){
    return (this.getCylinder() - obj.getCylinder()==0)?(int)(this.getRequestTime() - obj.getRequestTime()):this.getCylinder() - obj.getCylinder();
  }
  public boolean equals(DiskRequest other){
    return (this.compareTo(other) == 0);
  }
  public String toString(){
    return Fmt.chs(this.cylinder, this.head, this.sector);
  }
}
