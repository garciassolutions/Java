import java.util.Random;

/**
 *	Program to simulate various disk scheduling strategies.
 *
 *	@author Bruce Barton.
 *	@version Update 25 April 2009.
 */
public class DiskSimulator {
	//	Class symbolic constants which define the parameters of the simulation.

	public static final int    NUMBER_OF_REQUESTS = 50;
	public static final double GAUSSIAN_MEAN      = 1e4;
								// 10,000 (10 msec in µsec units)
	public static final double GAUSSIAN_STD       = 2e3;
								//	2,000 ( 2 msec in µsec units).

	//	Instance variables referencing simulation values.

	private EventQueue events;
	private DiskConfig config;
	private int maxSect;
	private long timer;
	private DiskDriver driver;

	/**
	 *	Default constructor.  Initializes simulation variables.
	 */
	public DiskSimulator ()
	{
		this.events = new EventQueue();
		this.config = new DiskConfig();
		this.maxSect = this.config.getMaxSectors();
		this.timer = 0;
		this.driver = null;
		//	Delay setting the driver until we run the simulation.
	}

	/**
	 *	Access to disk drive configuration information.
	 *
	 *	@return Object reference to DiskConfig object.
	 */
	public DiskConfig getConfig () { return this.config; }

	/**
	 *	Access the simulation timer.
	 *
	 *	@return The current timer's value.
	 */
	public long getTimer() { return this.timer; }

	/**
	 *	Place event on event queue for later processing.
	 *
	 *	@param ev Event object representing the type and time of event.
	 */
	public void addEvent (Event ev) { this.events.add(ev); }

	/**
	 *	Run a simulation using a specific disk drive scheduler.
	 *
	 *	@param theDriver Object reference to the driver.
	 */
	public void runSimulation (DiskDriver theDriver) {
		//	Set the driver based on the argument.
		this.driver = theDriver;

		//	Generate random disk request events and add them to the
		//	event queue.
		Random rand = new Random(NUMBER_OF_REQUESTS);
		Event ev = null;
		long nextEventTime = 0;

		for (int i = 0; i < NUMBER_OF_REQUESTS; i++) {
			ev = new Event(Event.NEW_REQUEST, nextEventTime);
			//Debug/System.out.println(">>> " + ev);
			this.events.add(ev);
			//	Random.nextGaussian() returns normally distributed values with
			//	mean of 0.0 and standard deviation of 1.0 (double data type).
			//	Adjust this to have a specified mean and a standard deviation.
			nextEventTime +=
				(long)(rand.nextGaussian() * GAUSSIAN_STD + GAUSSIAN_MEAN);
		}
		//Debug/System.out.println(">>> " + this.events.getSize() + " entries...");

		//	Run the simulation based on the disk requests.
		//	Reset the random sequence.
		rand = new Random(this.maxSect);
		//	Reset the timer back to 0.
		this.timer = 0;
		//	Reset the disk request sequence counter back to 0.
		DiskRequest.resetSeq();
		DiskRequest dr = null;
		int block;
		//	Statistical counters, sums, and averages.
		long waitTime, runTime;
		long waitSum = 0, runSum = 0;
		long waitAvg, runAvg;

		//	Event loop.
		//	Pick up an the next event and process it.
		//	When the event queue is empty, we are done!
		while ( ! this.events.isEmpty()) {
			ev = this.events.remove();
			this.timer = ev.getTime();
			switch (ev.getType()) {
			case Event.NEW_REQUEST:
				//	Tell driver to schedule a new request.
				block = rand.nextInt(maxSect);
				dr = new DiskRequest(block, this.timer, this.config);
				this.driver.schedRequest(dr);
				System.out.print(Fmt.time(timer) + ": request "
					+ Fmt.fill(Integer.toString(dr.getReqNum()), 2)
					+ " scheduled: ");
				System.out.println(dr);
				break;
			case Event.SEEK_SATISFIED:
				//	Send driver a seek interrupt.
				this.driver.seekInterrupt();
				break;
			case Event.ROT_SATISFIED:
				//	Send driver a rotation wait interrupt.
				this.driver.rotInterrupt();
				break;
			case Event.REQUEST_SATISFIED:
				//	Collect request statistics and tell driver to finish this
				//	request (driver will remove the old request and select a
				//	new request).
				//
				//	Reestablish dr from driver.
				//	Disk scheduler might have selected another request to work
				//	on.
				dr = this.driver.getRequest();
				waitTime = dr.getStartTime() - dr.getRequestTime();
				waitSum += waitTime;
				runTime = dr.getSatisfyTime() - dr.getStartTime();
				runSum += runTime;

				System.out.print(Fmt.time(timer) + ": request "
					+ Fmt.fill(Integer.toString(dr.getReqNum()), 2)
					+ " satisfied: ");
				System.out.print(dr);
				System.out.println(", wait:" +Fmt.time(waitTime)
					+ ", run:" + Fmt.time(runTime));
				this.driver.finishRequest();
				break;
			}
		}
		//	Print out overall statistics for the simulation.
		waitAvg = waitSum / NUMBER_OF_REQUESTS;
		runAvg = runSum / NUMBER_OF_REQUESTS;
		System.out.println("\n" + Fmt.time(this.timer)
			+ ":  Avg. wait: " + Fmt.time(waitAvg)
			+ ",  Avg. run:  " + Fmt.time(runAvg));
	}

	public static void main (String[] args) {
		//	Instantiate simulator object.
		DiskSimulator sim = new DiskSimulator();
		//	Display the disk configuration being simulated.
		System.out.println(sim.getConfig());
		System.out.println();

		//	Run a simulation using the fcfs scheduling algorithm.
		sim.runSimulation(new DiskDriver_fcfs(sim));
		System.out.println();

		//	Run a simulation using the elevator scheduling algorithm.
		sim.runSimulation(new DiskDriver_elevator(sim));
	}
}