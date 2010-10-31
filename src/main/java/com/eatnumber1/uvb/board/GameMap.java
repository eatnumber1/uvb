package com.eatnumber1.uvb.board;

import java.util.Map;

/**
 * @author Russell Harmon
 * @since 1:55:39 PM
 */
@SuppressWarnings({ "FieldCanBeLocal" })
public class GameMap {
	private Map<BoardObject, Character> objectTypes;
	private Map<Point, BoardObject> objects;
	private int radius;

	public GameMap( Map<BoardObject, Character> objectTypes, int radius, Map<Point, BoardObject> objects ) {
		this.objectTypes = objectTypes;
		this.radius = radius;
		this.objects = objects;
	}

	private char toChar( BoardObject obj ) {
		return objectTypes.get(obj);
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
					sb.append(toChar(objects.get(point)));
				} else {
					sb.append(toChar(BoardObject.EMPTY));
				}
			}
			sb.append("|\n");
		}
		sb.append('+');
		for( int i = -radius; i <= radius; i++ ) sb.append('-');
		sb.append("+");
		return sb.toString();
	}

	public BoardObject get( Point p ) {
		if( !objects.containsKey(p) ) return BoardObject.EMPTY;
		return BoardObject.valueOf(objects.get(p).toString());
	}
}
