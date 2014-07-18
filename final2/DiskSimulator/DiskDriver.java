/**
 *	Simulates the activity of a disk driver.
 *	<ol>
 *	<li>Takes in requests and schedules them.
 *	<li>Selects the next request and seeks to requested cylinder.
 *	<li>When cylinder is reached, wait for disk to rotate to proper sector.
 *	<li>When sector is located, satisfy request and move to next request.
 *	</ol>
 *
 *	@author Bruce Barton.
 *	@version Updated 25 April 2009.
 */
public abstract class DiskDriver
{
 	//	Local references to external object instances.
	protected DiskSimulator sim;
	protected DiskConfig config;

	//	Object instance data members.
	protected int cylinder;
	protected int head;
	protected int sector;
	protected DiskRequest request;
	protected long simTime;

	/**
	 *	Default Constructor.
	 */
	public DiskDriver ()
	{
		this.sim = null;
		this.config = null;
		this.request = null;
		this.simTime = 0;
		this.home();
	}

	/**
	 *	Complete Constructor.
	 *
	 *	@param theSim		Object reference to DiskSimulator object.
	 */
	public DiskDriver (DiskSimulator theSim)
	{
		this();
		this.sim = theSim;
		this.config = this.sim.getConfig();
	}

	/**
	 *	Accessor method.  Gets the current DiskRequest object.
	 *
	 *	@return The current DiskRequest object.
	 */
	public DiskRequest getRequest () { return this.request; }

	/**
	 *	Mutator method.  Resets read/write head arm to home position.
	 */
	public void home ()
	{
		this.sector = this.head = this.cylinder = 0;
	}

	/**
	 *	Create string to represent current location of read/write head.
	 *
	 *	@return "[C/H/S]" of current head location.
	 */
	@Override
	public String toString ()
	{
		return Fmt.chs(this.cylinder, this.head, this.sector);
	}

	/**
	 *	Compute and set current sector based on rotation time.
	 *	<p>
	 *	Disk keeps rotating.  It started out at sector 0.
	 *	Current sector is based solely on how long the simulation has
	 *	been running.
	 *	<p>
	 *	This mutator method sets the <code>sector</code> data member
	 *	to the position computed from the simulation time.
	 */
	private void setCurrSector ()
	{
		double rotTime = config.getRotTime();
		double rotPct = (double)(this.simTime % (long)rotTime) / rotTime;
		this.sector = (int)Math.round(rotPct * config.getSectors());
	}

	/**
	 *	Calculates the time it will take to move the read/write head from
	 *	the current cylinder position to the specified cylinder position.
	 *
	 *	@param newCylinder	Cylinder to which disk head is to be moved.
	 *	@return The number of &micro;secs needed to perform this movement.
	 */
	private long calcSeekTime (int newCylinder)
	{
		//	Compute number of cylinders to move.
		int moveCyl = Math.abs(newCylinder - this.cylinder);
		//	Compute time to move that many cylinders.
		long moveTime = (long)(2 * config.getRampTime());
		moveTime += Math.round(moveCyl * config.getTrack2Track());
		return moveTime;
	}

	/**
	 *	Calculates the time it will take for the disk to rotate from its
	 *	current position (sector location) to the desired position (sector
	 *	location).
	 *
	 *	@param newSector	Sector to which disk is to be rotated.
	 *	@return The number of &micro;secs needed to wait for this movement.
	 */
	private long calcRotTime (int newSector)
	{
		//	Compute number of sectors to wait until we rotate to where
		//	we should be.
		int moveSect = newSector - this.sector;
		//	If newSector < currSector, adjust from negative.
		if (moveSect < 0) moveSect += config.getSectors();
		//	Compute time to rotate into position.
		long moveTime = Math.round(moveSect * config.getRotSect());
		return moveTime;
	}

	/**
	 *	Schedule a disk request.
	 *	<p>
	 *	Abstract method.  Derived classes will specify scheduling
	 *	algorithm and thus define this method.
	 *	<p>
	 *	The simulator will call this method to have the driver schedule
	 *	an incoming disk request.
	 *
	 *	@param dr	Object reference to the DiskRequest object instance.
	 */
	public abstract void schedRequest (DiskRequest dr);

	/**
	 *	Select next request to process and set the request data member.
	 *	<p>
	 *	Abstract method.  Derived classes will specify scheduling
	 *	algorithm and thus define this method.
	 */
	protected abstract void nextRequest ();

	/**
	 *	Remove frontmost request (the one just processed) from the queue.
	 *	<p>
	 *	Abstract method.  Derived classes will specify scheduling
	 *	algorithm and thus define this method.
	 */
	protected abstract void removeRequest ();

	/**
	 *	This method will start off the processing of the next DiskRequest
	 *	object.
	 *	<p>
	 *	The driver will call this method when it has completed processing
	 *	the previous request and there are more requests to process.
	 *	<p>
	 *	The method will request a cylinder seek activity and post a seek
	 *	satisfied event to the simulator.
	 */
	public void procRequest ()
	{
		this.simTime = this.sim.getTimer();
		this.request.setStartTime(simTime);
		//Debug/System.out.println(Fmt.time(simTime)
		//Debug/	+ ":   <procRequest>      " + request);

		//	Need to move from current cylinder to requested cylinder.
		//	If current == requested, no action.
		if (this.cylinder != this.request.getCylinder())
			this.simTime += this.calcSeekTime(this.request.getCylinder());
		this.sim.addEvent(new Event(Event.SEEK_SATISFIED, this.simTime));
	}

	/**
	 *	The simulator will call this method when the seek has been
	 *	satisfied.
	 *	<p>
	 *	The method will adjust the driver's data members to reflect the
	 *	now current cylinder and head.  It will also determine the time
	 *	needed for the rotational delay and post a rotation satisfied
	 *	event to the simulator.
	 */
	public void seekInterrupt ()
	{
		this.simTime = this.sim.getTimer();
		//Debug/System.out.println(Fmt.time(simTime)
		//Debug/	+ ":   <seekInterrupt>    " + request);

		//	Set the cylinder, head, and sector.
		this.cylinder = this.request.getCylinder();
		this.head = this.request.getHead();
		//	Based on simulated time, determine current sector.
		this.setCurrSector();
		if (this.sector != this.request.getSector())
			this.simTime += this.calcRotTime(this.request.getSector());
		this.sim.addEvent(new Event(Event.ROT_SATISFIED, this.simTime));
	}

	/**
	 *	The simulator will call this method when the rotation has been
	 *	satisfied.
	 *	<p>
	 *	The method will adjust the driver's data members to reflect the
	 *	now current sector.  It will also mark the disk request as satisfied
	 *	and signal the simulator that it is finished with the request.
	 *	<p>
	 *	The method verifies that the sector under the read/write head (as
	 *	determined by simulated time) is, in fact, the requested sector.
	 *	If not, an error message is reported.
	 */
	public void rotInterrupt ()
	{
		this.simTime = this.sim.getTimer();
		//Debug/System.out.println(Fmt.time(this.simTime)
		//Debug/	+ ":   <rotInterrupt>     " + this.request);

		//	Based on simulated time, determine current sector.
		this.setCurrSector();

		//	Verify that the timing is correct and the sector we are at
		//	(based on simulated time) is the sector that we should be at.
		if (this.sector != this.request.getSector())
			System.err.println("sector error: " + this.request.getSector()
				+ " : " + this.sector);

		//	Update satisfy time in request object.
		this.request.setSatisfyTime(this.simTime);
		this.sim.addEvent(new Event(Event.REQUEST_SATISFIED, this.simTime));
	}

	/**
	 *	The simulator will call this method when it has finished processing the
	 *	request.
	 *	<p>
	 *	It signals the driver to select the next request and to start
	 *	processing the new request.
	 */
	public void finishRequest ()
	{
		this.simTime = this.sim.getTimer();
		//Debug/System.out.println(Fmt.time(this.simTime)
		//Debug/	+ ":   <finishRequest>     " + this.request);

		//	Remove request from the queue.
		this.removeRequest();
		//	Get the next request from the queue.
		this.nextRequest();
		//	If there is one, process it.
		//	If not, then the next request scheduled will begin immediately.
		if (this.request != null) this.procRequest();
	}
}