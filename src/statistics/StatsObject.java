package statistics;

public class StatsObject implements Comparable<StatsObject> {
	protected String myName;
	protected Integer myStatValue;
	
	public StatsObject(String statName, Integer statValue) {
		myName=statName;
		myStatValue=statValue;
	}
	
	public StatsObject(String statName) {
		myName=statName;
		myStatValue=0;
	}

	protected void changeStat(int changeAmount){
		myStatValue += changeAmount;
	}
	
	protected void setStat(int setAmount){
		myStatValue = setAmount;
	}
	
	public String toString(){
		return myName+": "+myStatValue;
	}
	
	public int compareTo(StatsObject compareStat) {
		return compareStat.myStatValue - this.myStatValue;	 
	}
	
}
