package statistics;

import java.util.List;

import jgame.JGColor;
import jgame.JGFont;
import jgame.platform.JGEngine;

public class StatsDisplay {
	private JGEngine myGameEngine;
	private int gameWidth;
	private int gameHeight; 
	private int SCREEN_WIDTH_SPACING;
	private static final int COLUMNS = 3;
	private static final int VERTICAL_SPACING = 20;
	private static final JGFont MAIN_HEADER_FONT=new JGFont("MAIN_HEADER_FONT", JGFont.BOLD, 24.0);
	private static final JGFont HEADER_FONT=new JGFont("HEADER_FONT", JGFont.BOLD, 16.0);
	private static final JGFont ENTRY_FONT=new JGFont("ENTRY_FONT", JGFont.PLAIN, 12.0);
	private static final JGColor HEADER_COLOR=JGColor.red;
	private static final JGColor ENTRY_COLOR=JGColor.white;

	public StatsDisplay(JGEngine engine) {
		myGameEngine=engine;
		gameWidth = myGameEngine.viewWidth();
		gameHeight = myGameEngine.viewHeight();
		SCREEN_WIDTH_SPACING=gameWidth/4;
	}

	protected void displayStats(String gameName, List<StatsObject> userStats, List<StatsList> allStatsLists){
		myGameEngine.drawString(gameName + " Stats", gameWidth/2, gameHeight/22, 0,MAIN_HEADER_FONT, HEADER_COLOR);
		displayUserStats(userStats);
		int listSize=allStatsLists.size();
		int displayHeight=gameHeight/6;
		int displayWidth=SCREEN_WIDTH_SPACING;
		boolean switchColumn=true;
		for (StatsList list :allStatsLists){
			paintList(list,displayWidth,displayHeight);
			if(switchColumn){
				displayWidth+=SCREEN_WIDTH_SPACING;
				if (displayWidth==COLUMNS*SCREEN_WIDTH_SPACING){
					switchColumn=false;
				}
			}
			else{
				displayWidth=SCREEN_WIDTH_SPACING;
				displayHeight+=(gameHeight*2)/listSize;
				switchColumn=true;
			}
		}
	}

	private void displayUserStats(List<StatsObject> userStats){
		int displayHeight=gameHeight/10;
		int displayWidth=gameWidth/2;
		String userString="Your Stats:  ";
		for (StatsObject currentStat :userStats){
			userString+=currentStat.toString()+"  ";
		}
		myGameEngine.drawString(userString, displayWidth, displayHeight, 0,ENTRY_FONT, ENTRY_COLOR);
	}

	private void paintList(StatsList list, int displayWidth,int displayHeight){
		myGameEngine.drawString(list.myStatName, displayWidth, displayHeight, 0,HEADER_FONT, HEADER_COLOR);
		displayHeight+=VERTICAL_SPACING;
		for(StatsObject stat: list.myUserStatsList){
			displayHeight+=VERTICAL_SPACING;
			paintStat(stat,displayWidth,displayHeight);
		}
	}

	private void paintStat(StatsObject stat, int displayWidth,int displayHeight) {
		myGameEngine.drawString(stat.toString(), displayWidth, displayHeight, 0,ENTRY_FONT, ENTRY_COLOR);
	}
}
