package gameEngine;

import java.util.*;

public class LevelInput {
	private Map<Character, String> characterMap;
	public LevelInput(Map<Character, String> charMap) {
		characterMap=charMap;
		//This class will deal with whole level actions like pausing and viewing main menu
	}
	public Map<Character, String> getCharMap(){
		return characterMap;
	}

}
