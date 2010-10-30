package com.eatnumber1.uvb.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Russell Harmon
 * @since 2:32:55 PM
 */
public class PointMapDeserializer implements JsonDeserializer<Map<Point, Character>> {
	@Override
	public Map<Point, Character> deserialize( JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext ) throws JsonParseException {
		Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
		Map<Point, Character> map = new HashMap<Point, Character>(entries.size());
		Pattern pattern = Pattern.compile("\\((-?\\d+), (-?\\d+)\\)");
		for( Map.Entry<String, JsonElement> i : entries ) {
			Matcher matcher = pattern.matcher(i.getKey());
			if( !matcher.matches() ) throw new JsonParseException("Unable to parse point " + i.getKey());
			map.put(new Point(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2))), i.getValue().getAsCharacter());
		}
		return map;
	}
}
