package data;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter extends PropertiesWriter {
	protected JsonAdapter<?>[] adapters;

	public JsonWriter(Map<String, List<Object>> map, JsonAdapter<?>... adapters) {
		super(map);
		this.adapters = adapters;
	}

	@Override
	public String toString() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		for (JsonAdapter<?> adapter : adapters) {
			gsonBuilder.registerTypeAdapter(adapter.getType(), adapter);
		}
		Gson gson = gsonBuilder.create();
		return gson.toJson(_objMap);
	}

}
