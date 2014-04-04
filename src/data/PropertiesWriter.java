package data;

import java.util.List;
import java.util.Map;

public abstract class PropertiesWriter {
	protected Map<String, List<Object>> _objMap;
	public PropertiesWriter(Map<String, List<Object>> objMap) {
		_objMap = objMap;
	}

	/**
	 * Returns representation of the object as a String
	 * @return String representation of the object
	 */
	public abstract String toString();
}
