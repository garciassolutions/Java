public interface WD300BB
{
	/** Name string for this disk device. */
	String NAME				= "WD Caviar 30 GB (WD300BB)";

	/** Number of cylinders / disk. */
	int    CYLINDERS		= 58168;
	/** Number of heads (tracks) / cylinder. */
	int    HEADS			=    16;
	/** Number of sectors / track. */
	int    SECTORS			=    63;
	/** Number of bytes in each sector. */
	int    BYTES_PER_SECTOR	=   512;

	/** The time, in microseconds, to ramp up the speed of the disk head
	 *	assembly from zero (0) to full speed. */
	double RAMP_TIME		=  1000.0;      // µsec
	/** The time, in microseconds, to move the disk head assembly from one
	 *	track to the next contiguous track. */
	double TRACK_TO_TRACK	=     0.326651; // µsec

	/** The number of revolutions / minute for this disk. */
	double ROTATIONAL_SPEED	=  7200.0;      // RPM
}