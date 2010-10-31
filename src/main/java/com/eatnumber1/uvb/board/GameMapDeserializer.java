package com.eatnumber1.uvb.board;

import com.eatnumber1.uvb.ProtocolException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class GameMapDeserializer implements JsonDeserializer<GameMap> {
	@Override
	public GameMap deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		final Map<BoardObject, Character> objectTypes = new HashMap<BoardObject, Character>();
		for( BoardObject boardObj : BoardObject.values() ) {
			if( !BoardObject.EMPTY.equals(boardObj) ) {
				objectTypes.put(boardObj, obj.getAsJsonPrimitive(boardObj.name()).getAsCharacter());
			} else {
				objectTypes.put(boardObj, ' ');
			}
		}
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(BoardObject.class, new JsonDeserializer<BoardObject>() {
			@Override
			public BoardObject deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {
				for( Entry<BoardObject, Character> e : objectTypes.entrySet() ) {
					if( e.getValue().equals(json.getAsCharacter()) ) return e.getKey();
				}
				throw new ProtocolException("Unknown object " + json.getAsCharacter());
			}
		});
		builder.registerTypeAdapter(Point.class, new PointDeserializer());
		Map<Point, BoardObject> objects = builder.create().fromJson(obj.getAsJsonObject("objects").toString(), new TypeToken<Map<Point, BoardObject>>() {
		}.getType());
		return new GameMap(objectTypes, obj.getAsJsonPrimitive("radius").getAsInt(), objects);
	}
}
