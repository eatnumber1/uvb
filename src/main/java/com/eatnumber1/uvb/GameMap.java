package com.eatnumber1.uvb;

import java.util.Map;

/**
 * @author Russell Harmon
 * @since 1:55:39 PM
 */
@SuppressWarnings({ "FieldCanBeLocal" })
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
		StringBuilder sb = new StringBuilder((int) Math.pow((radius + 1) * 2 + 1, 2) + (radius + 1) * 2);
		sb.append('+');
		for( int i = -radius; i <= radius; i++ ) sb.append('-');
		sb.append("+\n");
		for( int y = -radius; y <= radius; y++ ) {
			sb.append('|');
			for( int x = -radius; x <= radius; x++ ) {
				Point point = new Point(x, y);
				if( objects.containsKey(point) ) {
					sb.append(objects.get(point));
				} else {
					sb.append(' ');
				}
			}
			sb.append("|\n");
		}
		sb.append('+');
		for( int i = -radius; i <= radius; i++ ) sb.append('-');
		sb.append("+");
		return sb.toString();
	}
}
