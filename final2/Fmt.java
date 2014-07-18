import java.text.DecimalFormat;

/**
 *	A collection of <code>String</code> formatting tools.
 *
 *	@author Bruce Barton.
 *	@version Update 24 April 2009.
 */
public abstract class Fmt
{
	//	Output formats to represent [C/H/S], time (in seconds), and GBytes.
	static final DecimalFormat fmtCyl  = new DecimalFormat("##,##0");
	static final DecimalFormat fmtHS   = new DecimalFormat(    "#0");
	static final DecimalFormat fmtTime = new DecimalFormat("##,##0.000");
	static final DecimalFormat fmtGB   = new DecimalFormat(    "#0.000");

	static final double MSEC_PER_SEC   = 1000.0;
	static final int    CYL_WIDTH      =    6;
	static final int    HEAD_WIDTH     =    2;
	static final int    SECT_WIDTH     =    2;
	static final int    TIME_WIDTH     =   10;

	private Fmt () {};
	/**
	 *	Format the representation of a cylinder number.
	 *	This means add comma(s) to large numbers.
	 *
	 *	@param cylinder The cylinder number to be formatted.
	 *
	 *	@return The <code>String</code> representation.
	 */
	public static String formatCylinder (int cylinder)
	{
		return Fmt.fmtCyl.format(cylinder);
	}

	/**
	 *	Format the representation of a head number.
	 *
	 *	@param head The head number to be formatted.
	 *
	 *	@return The <code>String</code> representation.
	 */
	public static String formatHead (int head)
	{
		return Fmt.fmtHS.format(head);
	}

	/**
	 *	Format the representation of a sector number.
	 *
	 *	@param sect The sector number to be formatted.
	 *
	 *	@return The <code>String</code> representation.
	 */
	public static String formatSector (int sect)
	{
		return Fmt.fmtHS.format(sect);
	}

	/**
	 *	Format the representation of a gigabyte value.
	 *	This is rounded to the nearest megabyte in units of gigabytes.
	 *
	 *	@param gigabytes The number of gigabytes to be formatted.
	 *
	 *	@return The <code>String</code> representation.
	 */
	public static String formatGB (double gigabytes)
	{
		return Fmt.fmtGB.format(gigabytes);
	}

	/**
	 *	Format a time value.
	 *	<br>
	 *	The argument represents the number of milliseconds.
	 *	The result represents the number of seconds with a resolution
	 *	of one millisecond.
	 *
	 *	@param time The time value (in units of milliseconds) to be
	 *		converted into a <code>String</code>.
	 *
	 *	@return The <code>String</code> representing the time in units of
	 *		seconds but displayed to a precision of milliseconds.
	 */
	public static String formatTime (double time)
	{
		return Fmt.fmtTime.format(time / MSEC_PER_SEC);
	}

	/**
	 *	Create a <code>String</code> of <code>size</code> character which
	 *	contains the <code>str</code> right justified in the returned
	 *	<code>String</code>.
	 *
	 *	@param str The <code>String</code> to be inserted.
	 *	@param size The length of the generated <code>String</code> into
	 *		which the <code>str</code> will be inserted.
	 *
	 *	@return The created <code>String</code>.
	 */
	public static String fill (String str, int size)
	{
		StringBuilder sb = new StringBuilder();

		for (int n = size - str.length(); n > 0; n--)
			sb.append(' ');
		sb.append(str);

		return sb.toString();
	}

	/**
	 *	Format separate cylinder, head, and sector data into a single
	 *	<code>String</code>.
	 *
	 *	@param cylinder The cylinder value.
	 *	@param head The head value.
	 *	@param sector The sector value.
	 *
	 *	@return The <code>String</code> in the form: [cyl/hd/sect].
	 */
	public static String chs (int cylinder, int head, int sector)
	{
		StringBuilder sb = new StringBuilder("[");

		sb.append(Fmt.fill(Fmt.formatCylinder(cylinder), Fmt.CYL_WIDTH)).append('/');
		sb.append(Fmt.fill(Fmt.formatHead(head), Fmt.HEAD_WIDTH)).append('/');
		sb.append(Fmt.fill(Fmt.formatSector(sector), Fmt.SECT_WIDTH)).append(']');

		return sb.toString();
	}

	/**
	 *	Format a time field.
	 *	<br>
	 *	The argument represents the number of milliseconds.
	 *	The result represents the number of seconds with a resolution
	 *	of one millisecond.
	 *	The output <code>String</code> will be a filled field of 10 characters.
	 *
	 *	@param timer The time value (in units of milliseconds) to be
	 *		converted into a <code>String</code>.
	 *
	 *	@return The <code>String</code> representing the time in units of
	 *		seconds but displayed to a precision of milliseconds.
	 */
	public static String time (long timer)
	{
		double timeInSeconds = (double)timer / MSEC_PER_SEC;
		return Fmt.fill(Fmt.fmtTime.format(timeInSeconds), TIME_WIDTH);
	}
}