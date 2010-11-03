package com.eatnumber1.uvb;

import com.eatnumber1.uvb.ai.DecisionEngine;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.GameMapDeserializer;
import com.eatnumber1.uvb.commands.Command;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public class MoveRequestHandler implements RequestHandler {
	private static final Log log = LogFactory.getLog(MoveRequestHandler.class);

	private DecisionEngine engine;

	public MoveRequestHandler( DecisionEngine engine ) {
		this.engine = engine;
	}

	@Override
	public void run( Server server ) {
		String json = server.read();
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(GameMap.class, new GameMapDeserializer());
		GameMap map = builder.create().fromJson(json, GameMap.class);
		if( log.isInfoEnabled() ) {
			Scanner sc = new Scanner(map.render());
			while( sc.hasNextLine() ) {
				log.info(sc.nextLine());
			}
		}
		Command cmd = engine.decide(map);
		server.write(cmd.serialize());
	}
}
