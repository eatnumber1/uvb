package com.eatnumber1.uvb.board;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Russell Harmon
 * @since 2:32:55 PM
 */
public class PointDeserializer implements JsonDeserializer<Point> {
	//private static Pattern pattern = Pattern.compile("\\((-?\\d+), (-?\\d+)\\)");
	private static Pattern pattern = Pattern.compile("\\((-?\\d+(?:\\.\\d+)?), (-?\\d+(?:\\.\\d+)?)\\)");

	@Override
	public Point deserialize( JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext ) throws JsonParseException {
		Matcher matcher = pattern.matcher(jsonElement.getAsString());
		if( !matcher.matches() ) throw new JsonParseException("Unable to parse point " + jsonElement.getAsString());
		return new Point(Double.valueOf(matcher.group(1)).intValue(), Double.valueOf(matcher.group(2)).intValue());
		//return new Point(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
	}
}
