package com.eatnumber1.uvb.server;

import java.util.Map;

/**
 * @author Russell Harmon
 * @since 1:55:39 PM
 */
public class GameMap {

	private final Character SNOWMAN;

	private final Character TREE;

	private final Character PLAYER;

	private final Character SNOWBALL;

	private final Character EDGE;

	private final Character ME;

	private final Character UNKNOWN;

	private final Integer radius;

	private final Map<Point, Character> objects;

	// Needed by gson

	private GameMap() {
		this.SNOWMAN = null;
		this.TREE = null;
		this.PLAYER = null;
		this.SNOWBALL = null;
		this.EDGE = null;
		this.ME = null;
		this.UNKNOWN = null;
		this.radius = null;
		this.objects = null;
	}

	@Override
	public String toString() {
		return "GameMap{" +
				"SNOWMAN=" + SNOWMAN +
				", TREE=" + TREE +
				", PLAYER=" + PLAYER +
				", SNOWBALL=" + SNOWBALL +
				", EDGE=" + EDGE +
				", ME=" + ME +
				", UNKNOWN=" + UNKNOWN +
				", radius=" + radius +
				", objects=" + objects +
				'}';
	}
}
