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
		final Map<BoardObjectType, Character> objectTypes = new HashMap<BoardObjectType, Character>();
		for( BoardObjectType boardObjType : BoardObjectType.values() ) {
			if( !BoardObjectType.EMPTY.equals(boardObjType) ) {
				objectTypes.put(boardObjType, obj.getAsJsonPrimitive(boardObjType.name()).getAsCharacter());
			} else {
				objectTypes.put(boardObjType, ' ');
			}
		}
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(BoardObjectType.class, new JsonDeserializer<BoardObjectType>() {
			@Override
			public BoardObjectType deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {
				for( Entry<BoardObjectType, Character> e : objectTypes.entrySet() ) {
					if( e.getValue().equals(json.getAsCharacter()) ) return e.getKey();
				}
				throw new ProtocolException("Unknown object " + json.getAsCharacter());
			}
		});
		builder.registerTypeAdapter(Point.class, new PointDeserializer());
		Map<Point, BoardObjectType> objects = builder.create().fromJson(obj.getAsJsonObject("objects").toString(), new TypeToken<Map<Point, BoardObjectType>>() {
		}.getType());
		return new GameMap(objectTypes, obj.getAsJsonPrimitive("radius").getAsInt(), objects);
	}
}
