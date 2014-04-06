package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameData {
	private String _filename;
	private Map<String, List<Object>> objMap;

	/**
	 * Creates a GameData object initialized from a file. The file is saved if
	 * write() is called
	 * 
	 * @param filename
	 *            The name of the file
	 * @throws InvalidDataFileException
	 */
	public GameData(String filename) throws InvalidDataFileException {
		_filename = filename;
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

	public void setFileName(String filename) {
		_filename = filename;
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
	public void write() throws IOException {
		String jsonString = this.toString();

		File myFile = new File(_filename);
		if (!myFile.exists()) {
			myFile.createNewFile();
		}
		FileOutputStream fOut = new FileOutputStream(myFile);
		byte[] content = jsonString.getBytes();
		fOut.write(content);
		fOut.flush();
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

	public Map<String, List<Object>> parse() throws InvalidDataFileException {
		String jsonString = readFile();
		PropertiesReader jsonReader = new JsonReader(jsonString);
		try {
			objMap = jsonReader.parse();
		} catch (ClassNotFoundException e) {
			throw new InvalidDataFileException();
		}
		return objMap;
	}

	/**
	 * Returns a list of objects in the data file that match the class name(s)
	 * given
	 * 
	 * @param classNames
	 *            Name(s) of class(es) to retrieve
	 * @return List of objects with matching classes
	 * @throws ClassNotFoundException
	 *             When a class name is not found
	 */
	public List<Object> getObjects(String... classNames)
			throws ClassNotFoundException {
		List<Object> objs = new ArrayList<Object>();
		for (String className : classNames) {
			if (objMap.containsKey(className)) {
				objs.addAll(objMap.get(className));
			} else {
				throw new ClassNotFoundException(className);
			}
		}
		return objs;
	}
}
