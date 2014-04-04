package data;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class JsonWriter extends PropertiesWriter {

	public JsonWriter(Map<String, List<Object>> map) {
		super(map);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(_objMap);
	}

}
