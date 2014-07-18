public class MillisTime extends BaseTime {
	
	int milliseconds, msm;
	public static final int MILLIS_PER_DAY = SECONDS_PER_DAY*1000;
	public static final int MILLIS_PER_HOUR = SECONDS_PER_HOUR*1000;
	public static final int MILLIS_PER_MINUTE = SECONDS_PER_MINUTE*1000;
	
	public MillisTime(){
		this.setHours(0);
		this.setMinutes(0);
		this.setSeconds(0);
		this.milliseconds = 0;
	}
	public MillisTime(int MSM){
		this.milliseconds = (MSM%1000);
		this.setHours((int)((MSM-this.milliseconds)/MILLIS_PER_HOUR));
		this.setMinutes((int)(MSM-(this.getHours()*MILLIS_PER_HOUR)/MILLIS_PER_MINUTE));
		this.setSeconds((int)((MSM-(this.getHours()*MILLIS_PER_HOUR))-(this.getMinutes()*MILLIS_PER_MINUTE)));
	}
	public MillisTime(int Hours, int Min){
		this.setHours(Hours);
		this.setSeconds(0);
		this.milliseconds = 0;
	}
	public MillisTime(int Hours, int Min, int Seconds){
		this.setHours(Hours);
		this.setMinutes(Min);
		this.setSeconds(Seconds);
		this.milliseconds = 0;
	}
	public MillisTime(int Hours, int Min, int Seconds, int Milliseconds){
		this.setHours(Hours);
		this.setMinutes(Min);
		this.setSeconds(Seconds);
		if(Milliseconds >= 0 && Milliseconds < 1000) this.milliseconds = Milliseconds;
		else throw new IllegalArgumentException("[-] Millisecond must be a value between zero and one thousand.");
	}
	public String toString(){
		return String.format("%02d:%02d:%02d.%03d", this.getHours(), this.getMinutes(), this.getSeconds(), this.milliseconds);
	}
	public int getMilliSecondsSinceMidnight(){
		return this.msm;
	}
	
}
