package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader extends PropertiesReader {
	protected JsonAdapter<?>[] adapters;

	public JsonReader(String serializedText, JsonAdapter<?>... adapters) {
		super(serializedText);
		this.adapters = adapters;
	}

	@Override
	public Map<String, List<Object>> parse() throws ClassNotFoundException {
		// https://code.google.com/p/google-gson/source/browse/trunk/extras/src/main/java/com/google/gson/extras/examples/rawcollections/RawCollectionsExample.java
		JsonParser parser = new JsonParser();
		JsonObject gameObj = parser.parse(_serializedText).getAsJsonObject();
		GsonBuilder gsonBuilder = new GsonBuilder();
		for (JsonAdapter<?> adapter : adapters) {
			gsonBuilder.registerTypeAdapter(adapter.getType(), adapter);
		}
		Gson gson = gsonBuilder.create();
		Map<String, List<Object>> objMap = new HashMap<String, List<Object>>();
		for (Entry<String, JsonElement> el : gameObj.entrySet()) {
			List<Object> objs = new ArrayList<Object>();
			Class<?> klass = Class.forName(el.getKey());
			JsonArray array = (JsonArray) el.getValue();
			for (JsonElement jsonObj : array) {
				objs.add(gson.fromJson(jsonObj, klass));
			}
			objMap.put(klass.getName(), objs);
		}
		return objMap;
	}

}
