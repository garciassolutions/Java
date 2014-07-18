public class DiskRequest implements Comparable<DiskRequest>
{
	private static int seq         = 0;
	private        int reqNum      = 0;
	private        int cylinder    = 0;
	private        int head        = 0;
	private        int sector      = 0;
	private       long requestTime = 0;
	private       long startTime   = 0;
	private       long satisfyTime = 0;

	public DiskRequest()
	{
		seq++;
		reqNum = seq;
	}

	public DiskRequest(int cylinderIn, int headIn, int sectorIn, long requestTimeIn)
	{
		seq++;
		reqNum           = seq;
		this.cylinder    = cylinderIn;
		this.head        = headIn;
		this.sector      = sectorIn;
		this.requestTime = requestTimeIn;
	}

	public DiskRequest(int block, long requestTimeIn, DiskConfig configIn)
	{
		seq++;
		reqNum = seq;

		//convert from block to CHS
		this.cylinder = block / (configIn.getHeads()   * configIn.getSectors());
		     int TEMP = block % (configIn.getHeads()   * configIn.getSectors());
		this.head     = TEMP  /  configIn.getSectors();
		this.sector   = TEMP  %  configIn.getSectors();

		//http://en.wikipedia.org/wiki/CHS_conversion

		this.requestTime = requestTimeIn;

	}

	public int  getReqNum()      { return this.reqNum;      }
	public int  getCylinder()    { return this.cylinder;    }
	public int  getHead()        { return this.head;        }
	public int  getSector()      { return this.sector;      }
	public long getRequestTime() { return this.requestTime; }
	public long getStartTime()   { return this.startTime;   }
	public long getSatisfyTime() { return this.satisfyTime; }

	public long setStartTime(long startTimeIn)
	{
		//set the start time instance varaible
		this.startTime = startTimeIn;
		return this.startTime;
	}
	public long setSatisfyTime(long satisfyTimeIn)
	{
		//set the satisfytime time instance varaible
		this.satisfyTime = satisfyTimeIn;
		return this.satisfyTime;
	}

	public static void resetSeq()
	{
		seq = 0;
	}

	public String toString()
	{
		return Fmt.chs(this.cylinder, this.head, this.sector);
	}

	public int compareTo(DiskRequest that)
	{
		int result = this.getCylinder() - that.getCylinder();
		if (result == 0) result = (int)(this.getRequestTime() - that.getRequestTime());
		return result;
	}

	public boolean equals(DiskRequest that)
	{
		return (this.compareTo(that) == 0);
	}

}