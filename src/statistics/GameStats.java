package statistics;
import java.util.ArrayList;
import java.util.List;

public class GameStats {
	
	private static List<StatsObject> allStatsObjects = new ArrayList<StatsObject>();
	
	public GameStats(){
		
	}
	
	protected static List<StatsObject> getAllStatsObjects(){
		return allStatsObjects;
	}
	
	private static StatsObject getStatsObject(String statToGet){
		for(StatsObject so : allStatsObjects){
			if(so.myName.equals(statToGet)){
				return so;
			}
		}
		return addStatsObject(statToGet);
	}
	
	private static StatsObject addStatsObject(String statName){
		StatsObject temp = new StatsObject(statName);
		allStatsObjects.add(temp);
		return temp;
	}
	
	public static void update(String statToChange, int amount){
		getStatsObject(statToChange).changeStat(amount);
//		System.out.println("updating: " + statToChange + " by " + amount);
	}
	
	public static void set(String statToSet, int amount){
		getStatsObject(statToSet).setStat(amount);
	}

}
