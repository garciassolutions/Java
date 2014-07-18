class DiskDriver_fcfs extends DiskDriver{
  private Queue<DiskRequest> request_queue;
  public DiskDriver_fcfs(DiskSimulator sim){
    super(sim);
    request_queue = new Queue<DiskRequest>();
  }
  public void schedRequest(DiskRequest req){
    this.request_queue.add(req);
    if(this.request == null){
      nextRequest();
      procRequest();
    }    
  }
  public void removeRequest(){
    request_queue.remove(); // Remove an element from the front of the queue.
  }
  public void nextRequest(){
    this.request = (this.request_queue.isEmpty())?null:request_queue.get();
  }
}
