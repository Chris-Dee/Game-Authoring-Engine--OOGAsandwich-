package data;

import java.util.List;
import java.util.Map;

public abstract class PropertiesReader {
	protected String _serializedText;

	public PropertiesReader(String serializedText) {
		_serializedText = serializedText;
	}

	/**
	 * Parses serialized object and returns a Map mapping the class name (as
	 * String) to a list of the objects of that type
	 * 
	 * @param serializedText Text representing serialized version of the object
	 * @return Map mapping the class name (as String) to a list of the objects
	 *         of that type
	 * @throws ClassNotFoundException 
	 */
	public abstract Map<String, List<Object>> parse() throws ClassNotFoundException;
}
