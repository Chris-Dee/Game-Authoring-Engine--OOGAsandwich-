package data;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import data.JsonAdapter;

public class GenericJsonAdapter<T> implements JsonAdapter<T> {
	protected T type;
	protected Class<?> klass;
	protected String packageName;

	public GenericJsonAdapter(Class<?> klass, String packageName) {
		this.packageName = packageName;
		this.klass = klass;
	}

	@Override
	public T deserialize(JsonElement jsonEl, Type typeOfObject,
			JsonDeserializationContext context) throws JsonParseException {
		T object = null;
		JsonObject jsonObj = jsonEl.getAsJsonObject();
		String type = jsonObj.get("type").getAsString();
		JsonElement propertiesEl = jsonObj.get("properties");
		try {
			object = context.deserialize(propertiesEl,
					Class.forName(String.format("%s.%s", packageName, type)));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public JsonElement serialize(T obj, Type type,
			JsonSerializationContext context) {
		JsonObject jsonEl = new JsonObject();
		jsonEl.add("type", new JsonPrimitive(obj.getClass().getSimpleName()));
		jsonEl.add("properties", context.serialize(obj, obj.getClass()));
		return jsonEl;
	}

	@Override
	public Class<?> getType() {
		return klass;
	}

}
