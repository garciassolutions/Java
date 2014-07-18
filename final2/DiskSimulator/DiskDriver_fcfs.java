public class DiskDriver_fcfs extends DiskDriver
{
	Queue<DiskRequest> requestList;

	//set up sim and list.
	public DiskDriver_fcfs(DiskSimulator theSim)
	{
		super(theSim);
		requestList = new Queue<DiskRequest>();
	}

	//schedule the request in the queue
	public void schedRequest(DiskRequest dr)
	{
		requestList.add(dr);

		if(request == null)
		{
			nextRequest();
			procRequest();
		}
	}
	//remove the first element from the evenet queue after prosessing
	protected void removeRequest()
	{
		requestList.remove();
	}

	//retrieve but not remove the next request.
	protected void nextRequest()
	{
		request = (requestList.isEmpty()) ? null:(DiskRequest)requestList.get();
	}
}