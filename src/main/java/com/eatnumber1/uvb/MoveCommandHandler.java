package com.eatnumber1.uvb;

import com.eatnumber1.uvb.server.GameMap;
import com.eatnumber1.uvb.server.Point;
import com.eatnumber1.uvb.server.PointDeserializer;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public class MoveCommandHandler implements CommandHandler {
	@Override
	public void run( PrintWriter out, Scanner in ) {
		String json = in.nextLine();
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Point.class, new PointDeserializer());
		GameMap map = builder.create().fromJson(json, GameMap.class);
		System.out.println(map);
	}
}
