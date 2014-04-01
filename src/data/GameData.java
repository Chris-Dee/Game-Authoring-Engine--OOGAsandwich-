package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class GameData {
	private String _filename;
	private HashMap<String, Object> objMap = new HashMap<String, Object>();

	/**
	 * Creates a GameData object initialized from a file. If the file exists, it
	 * is parsed. If the file does not exist, then it is created when save() is
	 * called.
	 * 
	 * @param filename The name of the file
	 */
	public GameData(String filename) {
		_filename = filename;
		readFile();
	}

	/**
	 * Adds obj to be serialized. Its class is determined and it is placed into
	 * a section corresponding to its class.
	 * 
	 * @param obj An object that is to be added to be serialized.
	 * @return
	 */
	public void addObj(Object obj) {
		String klass = obj.getClass().getName();
		objMap.put(klass, obj);
	}

	/**
	 * Returns the JSON representation of the GameData
	 */
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(objMap);
	}

	private String readFile() {
		String fileText = null;
		try {
			fileText = new Scanner(new File(_filename)).useDelimiter("\\A")
					.next();
		} catch (FileNotFoundException e) {
			// Do nothing, we just need to write to this file
		}
		return fileText;
	}

	private String objToJSON(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	private void parseJSON(String jsonString) {
		// https://code.google.com/p/google-gson/source/browse/trunk/extras/src/main/java/com/google/gson/extras/examples/rawcollections/RawCollectionsExample.java
		JsonParser parser = new JsonParser();
		JsonObject gameObj = parser.parse(jsonString).getAsJsonObject();
		for (Entry<String, JsonElement> el : gameObj.entrySet()) {
			System.out.printf("%s: %s\n", el.getKey(), el.getValue());
		}
	}
}
