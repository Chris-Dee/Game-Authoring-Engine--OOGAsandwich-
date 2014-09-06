package statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import jgame.platform.JGEngine;

/**
 * @author Sam Ginsburg and Ethan Gottlieb
 *
 */
public class StatsController {
	
	protected static final String PATH = "src/statistics/PersistentStats.txt";
	protected static final int SIZE_OF_STATS_LIST = 5;

	protected StatsDisplay myStatsDisplay;
	protected JGEngine myEngine;
	protected String myGameName;
	protected List<StatsList> loadedStats;
	protected List<StatsObject> userStats;
	
	public StatsController(JGEngine gameEngine, String gameName) {
		myStatsDisplay=new StatsDisplay(gameEngine);
		myEngine = gameEngine;
		myGameName = gameName;
	}

	public void displayStats(){
		myStatsDisplay.displayStats(myGameName, userStats,loadedStats);
	}
	
	private List<StatsList> loadStats(){

		System.out.println("loading stats");
		List<StatsList> allStats = new ArrayList<StatsList>();
		Map<String, StatsList> allStatsMap = new HashMap<String, StatsList>();

		try{
			Scanner sc = new Scanner(new File(PATH));

			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] tempAr = line.split(",");
				System.out.println(tempAr[3]);
				System.out.println(tempAr[3].getClass());
				Integer valueInt = Integer.parseInt(tempAr[3]);
				if(!tempAr[1].equals(myGameName)){
					continue;
				}
				StatsObject tempUserStat = new StatsObject(tempAr[2], valueInt);
				String statName = tempAr[0];
				if(!allStatsMap.containsKey(statName)){
					allStatsMap.put(statName, new StatsList(statName, new ArrayList<StatsObject>()));
				}
				allStatsMap.get(statName).myUserStatsList.add(tempUserStat);
			}
			sc.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		allStats.addAll(allStatsMap.values());

		loadedStats = allStats;
		sortUserStats();
		chopOffUserStats();

		return allStats;
	}


	public void saveGameStats(){
		String inputName = JOptionPane.showInputDialog("Your name?");
		save(inputName);
		loadStats();
	}
	
	private void save(String userName){
		System.out.println("saving stats");
		
		userStats = GameStats.getAllStatsObjects();
		
		try{
		
			File file = new File(PATH);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (StatsObject so : GameStats.getAllStatsObjects()) {
				String toWrite = so.myName + "," + myGameName + "," + userName + "," + so.myStatValue.toString() + "\n";
				bw.write(toWrite);
			}
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void sortUserStats(){
		for(StatsList sl : loadedStats){
			Collections.sort(sl.myUserStatsList);
		}
	}
	
	private void chopOffUserStats(){
		for(StatsList sl : loadedStats){
			if(sl.myUserStatsList.size() > SIZE_OF_STATS_LIST){
				sl.myUserStatsList = sl.myUserStatsList.subList(0, SIZE_OF_STATS_LIST);
			}
		}
	}

}
