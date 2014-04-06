package gameEngine;

import java.util.*;

public class LevelInput {
	private Map<Character, String[]> characterMap;
	public LevelInput(Map<Character, String[]> charMap) {
		characterMap=charMap;
	}
	public Map<Character, String[]> getCharMap(){
		return characterMap;
	}

}
