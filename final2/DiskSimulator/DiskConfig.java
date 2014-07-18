/**
 *	Utility class to access disk configuration information.
 *
 *	@author Bruce Barton.
 *	@version Updated 25 April 2009.
 */
public class DiskConfig
	implements WD300BB
{
	//	Various constant relationships.

	static final double BYTES_PER_GB   = 1e9;
	static final double SECS_PER_MIN   = 6e1;
	static final double USECS_PER_SEC  = 1e6;
	static final double USECS_PER_MSEC = 1e3;
	static final double USECS_PER_MIN  = USECS_PER_SEC * SECS_PER_MIN;

	//	Instance variables for this class.

	private String name;

	//	Disk geometry.

	private int    cylinders;		// per disk
	private int    heads;			// (tracks) per cylinder
	private int    sectors;			// per track
	private int    bytesPerSector;

	//	Disk performance.

	private double rampTime;		// µsec
	private double track2track;		// µsec
	private double rotSpeed;		// rpm

	/**
	 *	Constructor sets instance variable from device interface.
	 */
	public DiskConfig ()
	{
		this.name           = WD300BB.NAME;

		//	Disk geometry.

		this.cylinders      = WD300BB.CYLINDERS;
		this.heads          = WD300BB.HEADS;
		this.sectors        = WD300BB.SECTORS;
		this.bytesPerSector = WD300BB.BYTES_PER_SECTOR;

		//	Disk performance.

		this.rampTime       = WD300BB.RAMP_TIME;
		this.track2track    = WD300BB.TRACK_TO_TRACK;
		this.rotSpeed       = WD300BB.ROTATIONAL_SPEED;
	}

	//	Accessors for disk geometry.

	public int getCylinders ()      { return this.cylinders; }
	public int getHeads ()          { return this.heads; }
	public int getSectors ()        { return this.sectors; }
	public int getBytesPerSector () { return this.bytesPerSector; }

	/**
	 *	Get the total number of sectors for this disk.
	 *
	 *	@return The total number of sectors for this disk.
	 */
	public int getMaxSectors ()
	{
		return this.cylinders * this.heads * this.sectors;
	}

	/**
	 *	Get the disk capacity (in gigabytes) for this disk.
	 *
	 *	@return The number of gigabyte which can be stored on this disk.
	 */
	public double getCapacity ()
	{
		double bytes = this.getMaxSectors();
		bytes *= this.bytesPerSector;
		return bytes / BYTES_PER_GB;
	}

	//	Accessors for disk performance.

	public double getRampTime ()    { return this.rampTime; }
	public double getTrack2Track () { return this.track2track; }
	public double getRotSpeed ()    { return this.rotSpeed; }

	/**
	 *	Get the time for a single disk rotation (in microseconds).
	 *
	 *	@return The rotational time in microseconds.
	 */
	public double getRotTime ()
	{
		return USECS_PER_MIN / this.rotSpeed;	// µsec / revolution
	}

	/**
	 *	Get the average (statistically expected) rotational time
	 *	(in milliseconds).
	 *	<br>
	 *	On average the disk will need to rotate &frac12; revolution to
	 *	locate the desired sector.
	 *
	 *	@return The expected rotational time in milliseconds.
	 */
	public double getAvgRotTime ()
	{
		return Math.round(getRotTime() / 2.0) / USECS_PER_MSEC;
	}

	/**
	 *	Get the time is takes to rotate over a single sector.
	 *	<br>
	 *	Since there are <code>sectors</code> sectors per track (one full
	 *	revolution) then the time to rotate over a single sector is the time
	 *	to rotate one full revolution divided by the number of sectors per
	 *	track.
	 *
	 *	@return Then time (in microseconds) to rotate over a single sector.
	 */
	public double getRotSect ()
	{
		return getRotTime() / sectors;
	}

	/**
	 *	String representation of all of the disk parameters
	 *	(disk geometry and disk performance).
	 *
	 *	@return The desired <code>String</code> representation.
	 */
	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder("Disk drive configuration...");

		sb.append("\nDrive name:			");
		sb.append(this.name);
		sb.append("\nCylinders / drive:		");
		sb.append(Fmt.formatCylinder(this.cylinders));
		sb.append("\nTracks / cylinder:		");
		sb.append(Fmt.formatHead(this.heads));
		sb.append("\nSectors / track:		");
		sb.append(Fmt.formatSector(this.sectors));
		sb.append("\nTotal sectors:			");
		sb.append(Fmt.formatCylinder(this.getMaxSectors()));
		sb.append("\nTotal capacity:			");
		sb.append(Fmt.formatGB(this.getCapacity())).append(" GB");
		sb.append("\nAverage rotational time:	");
		sb.append(Fmt.formatTime(this.getAvgRotTime())).append(" msec");

		return sb.toString();
	}
}