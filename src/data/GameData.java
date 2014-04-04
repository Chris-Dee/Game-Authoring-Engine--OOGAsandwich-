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
import java.util.Scanner;

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
		PropertiesWriter writer = new JsonWriter(objMap);
		return writer.toString();
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
			throws ClassNotFoundException {
		PropertiesReader jsonReader = new JsonReader(jsonString);
		objMap = jsonReader.parse();
		return objMap;
	}
}