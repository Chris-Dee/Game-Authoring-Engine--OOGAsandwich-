package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class GameData {
	private String _filename;
	private Map<String, List<Object>> objMap;

	/**
	 * Creates a GameData object initialized from a file. If the file exists, it
	 * is parsed. If the file does not exist, then it is created when save() is
	 * called.
	 * 
	 * @param filename
	 *            The name of the file
	 */
	public GameData(String filename) {
		_filename = filename;
		readFile();
		objMap = new HashMap<String, List<Object>>();
	}

	/**
	 * Adds obj to be serialized. Its class is determined and it is placed into
	 * a section corresponding to its class.
	 * 
	 * @param obj
	 *            An object that is to be added to be serialized.
	 * @return
	 */
	public void addObj(Object obj) {
		String klass = obj.getClass().getName();
		if (!objMap.containsKey(klass)) {
			ArrayList<Object> objList = new ArrayList<Object>();
			objMap.put(klass, objList);
		}
		objMap.get(klass).add(obj);
	}

	/**
	 * Returns the JSON representation of the GameData
	 */
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(objMap);
	}

	/**
	 * Writes the contents of the objMap to a file.
	 * 
	 * @param filename
	 *            The file to be written to.
	 * @return
	 */
	public void write(String filename) throws IOException {
		String jsonString = this.toString();
		
		File myFile = new File(filename);
		myFile.createNewFile();
		FileOutputStream fOut = new FileOutputStream(myFile);
		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		myOutWriter.append(jsonString);
		myOutWriter.close();
		fOut.close();
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

	private Map<String, List<Object>> parseJSON(String jsonString)
			throws JsonSyntaxException, ClassNotFoundException {
		// https://code.google.com/p/google-gson/source/browse/trunk/extras/src/main/java/com/google/gson/extras/examples/rawcollections/RawCollectionsExample.java
		JsonParser parser = new JsonParser();
		JsonObject gameObj = parser.parse(jsonString).getAsJsonObject();
		Gson gson = new Gson();
		Map<String, List<Object>> readMap = new HashMap<String, List<Object>>();
		for (Entry<String, JsonElement> el : gameObj.entrySet()) {
			List<Object> objs = new ArrayList<Object>();
			Class klass = Class.forName(el.getKey());
			JsonArray array = (JsonArray) el.getValue();
			for (JsonElement jsonObj : array) {
				objs.add(gson.fromJson(jsonObj, klass));
			}
			readMap.put(klass.getName(), objs);
		}
		objMap = readMap;
		return readMap;
	}
}