package statistics;

import java.util.List;

public class StatsList {
	
	protected String myStatName;
	protected List<StatsObject> myUserStatsList;
	
	public StatsList(String statName, List<StatsObject> allUserStats) {
		myStatName=statName;
		myUserStatsList=allUserStats;
	}

}
