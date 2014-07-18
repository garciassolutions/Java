public class BaseTime {
	
	public static final int HOURS_PER_DAY = 24; // Hours per day
	public static final int MINUTES_PER_HOUR = 60; // Minutes per hour
	public static final int SECONDS_PER_MINUTE = 60; // Seconds per minute
	public static final int MINUTES_PER_DAY = MINUTES_PER_HOUR*HOURS_PER_DAY;
	public static final int SECONDS_PER_DAY = SECONDS_PER_MINUTE*MINUTES_PER_DAY;
	public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE*MINUTES_PER_HOUR;
	public static final int SECONDS_SINCE_MIDNIGHT_MAX = (MINUTES_PER_DAY * SECONDS_PER_MINUTE)-1;
	
	private int hours, minutes, seconds, ssm;
	
	public BaseTime(){ // Set everything to 0.
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.ssm = 0;
	}
	public BaseTime(int SSM){
		if(SSM <= SECONDS_SINCE_MIDNIGHT_MAX) this.ssm = SSM;
		else throw new IllegalArgumentException("[-] Seconds since midnight cannot be greater than " + SECONDS_SINCE_MIDNIGHT_MAX);
		this.hours = (int)(SSM/SECONDS_PER_HOUR);
		this.minutes = (int)((SSM-(this.hours*SECONDS_PER_HOUR))/SECONDS_PER_MINUTE);
		this.seconds = SSM%SECONDS_PER_MINUTE;
	}
	public BaseTime(int Hours, int Min){
		if(Hours < 24 && Hours >= 0) this.hours = Hours;
		else throw new IllegalArgumentException("[-] Hours must be a value between zero and twentyfour.");
		if(Min < 60 && Min >= 0) this.minutes = Min;
		else throw new IllegalArgumentException("[-] Minutes must be a value between zero and sixty.");
		this.seconds = 0;
		this.ssm = this.seconds + (this.minutes*SECONDS_PER_MINUTE) + (this.hours*SECONDS_PER_HOUR);
	}
	public BaseTime(int Hours, int Min, int Sec){
		if(Hours < 24 && Hours >= 0) this.hours = Hours;
		else throw new IllegalArgumentException("[-] Hours must be a value between zero and twentyfour.");
		if(Min < 60 && Min >= 0) this.minutes = Min;
		else throw new IllegalArgumentException("[-] Minutes must be a value between zero and sixty.");
		if(Sec >= 0 && Sec < 60) this.seconds = Sec;
		else throw new IllegalArgumentException("[-] Seconds must be a value between zero and sixty.");
		this.ssm = this.seconds + (this.minutes*SECONDS_PER_MINUTE) + (this.hours*SECONDS_PER_HOUR);
	}
	public int getHours(){
		return this.hours;
	}
	public int getMinutes(){
		return this.minutes;
	}
	public int getSeconds(){
		return this.seconds;
	}
	public int getSecondsSinceMidnight(){
		return this.ssm;
	}
	public void setHours(int Hours) throws IllegalArgumentException{
		if(Hours < HOURS_PER_DAY && Hours >= 0) this.hours = Hours;
		else throw new IllegalArgumentException("[-] Hours must be a value between zero and twentyfour.");
	}
	public void setMinutes(int Minutes) throws IllegalArgumentException{
		if(Minutes < MINUTES_PER_HOUR && Minutes >= 0) this.minutes = Minutes;
		else throw new IllegalArgumentException("[-] Minutes must be a value between zero and sixty.");
	}
	public void setSeconds(int Seconds) throws IllegalArgumentException{
		if(Seconds < SECONDS_PER_MINUTE && Seconds >= 0) this.seconds = Seconds;
		else throw new IllegalArgumentException("[-] Seconds must be a value between zero and sixty.");
	}
	public int compareTo(Object CMP){
		if(((BaseTime)CMP).hours == this.hours && ((BaseTime)CMP).minutes == this.minutes && ((BaseTime)CMP).seconds == this.seconds) return 0;
		else if((((BaseTime)CMP).hours > this.hours) || ((((BaseTime)CMP).hours == this.hours) && (((BaseTime)CMP).minutes > this.minutes)) || ((((BaseTime)CMP).hours == this.hours) && (((BaseTime)CMP).minutes == this.minutes) && (((BaseTime)CMP).seconds > this.seconds))) return ((((BaseTime)CMP).ssm)-this.ssm);
		else return (this.ssm-(((BaseTime)CMP).ssm));
	}
	public BaseTime diff(BaseTime CMP){
		int ssm2 = ((this.seconds-CMP.seconds) + ((this.minutes-CMP.minutes)*SECONDS_PER_MINUTE) + (((this.hours-CMP.hours)*MINUTES_PER_HOUR)*SECONDS_PER_MINUTE));
		if(ssm2 < 0) ssm2 *= -1;
		return new BaseTime(ssm2);
	}
	public BaseTime plus(BaseTime CMP){
		int ssm2 = ((this.seconds+CMP.seconds) + ((this.minutes+CMP.minutes)*SECONDS_PER_MINUTE) + (((this.hours+CMP.hours)*MINUTES_PER_HOUR)*SECONDS_PER_MINUTE));
		return new BaseTime(ssm2);
	}
	public void add(BaseTime CMP){
		int ssm2 = ((this.seconds+CMP.seconds) + ((this.minutes+CMP.minutes)*SECONDS_PER_MINUTE) + (((this.hours+CMP.hours)*MINUTES_PER_HOUR)*SECONDS_PER_MINUTE));
		BaseTime tmp = new BaseTime(ssm2);
		this.setHours(tmp.hours);
		this.setMinutes(tmp.minutes);
		this.setSeconds(tmp.seconds);
	}
	public String toString(){
		return String.format("%02d:%02d:%02d", this.hours, this.minutes, this.seconds);
	}
	public boolean equals(Object CMP){
		if(CMP instanceof BaseTime) return true;
		else return false;
	}
}
