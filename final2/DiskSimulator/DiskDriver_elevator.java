class DiskDriver_elevator extends DiskDriver{
  private static final int UP = 0;
  private static final int DN = 1;
  private int dir;
  boolean i = false;
  private PriorityQueue<DiskRequest> requests[];

  @SuppressWarnings("unchecked")
  public DiskDriver_elevator(DiskSimulator sim){
    super(sim);
    requests = new PriorityQueue[2];
    requests[UP] = new PriorityQueueAsc<DiskRequest> ();
    requests[DN] = new PriorityQueueDesc<DiskRequest> ();
    dir = UP; // Set the elevator direction.
  }

  public void schedRequest(DiskRequest req){
    if(this.request == null){
      requests[dir].add(req);
      nextRequest();
      procRequest();
      removeRequest();
    }
    else{
      requests[(req.compareTo(request) <= 0)?DN:UP].add(req);
    }
  }
  public void removeRequest(){
    this.requests[dir].remove(); // Remove from the end of the list.
  }
  public void nextRequest(){
    if(requests[dir].isEmpty()) dir ^= 1;
    this.request = (requests[dir].isEmpty())?null:requests[dir].get();
  }
}
