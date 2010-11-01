package com.eatnumber1.uvb.board;

import com.eatnumber1.uvb.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Map.Entry;

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
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i <= radius; i++ ) sb.append(' ');
		sb.append("-\n");

		int last = -1;
		for( int y = -radius; y <= radius; y++ ) {
			double theta = Math.asin((double) y / radius);
			long d = Math.round(radius * Math.cos(theta));
			if( d > Integer.MAX_VALUE ) throw new IndexOutOfBoundsException("Number too big");
			int x = (int) d;
			for( int i = 0; i < radius - x; i++ ) sb.append(' ');
			sb.append(x > last ? '/' : x < last ? '\\' : '|');
			for( int i = -x; i <= x; i++ ) {
				Point point = new Point(i, y);
				if( objects.containsKey(point) ) {
					sb.append(toChar(objects.get(point)));
				} else {
					sb.append(toChar(BoardObject.EMPTY));
				}
			}
			sb.append(x > last ? '\\' : x < last ? '/' : '|').append('\n');
			last = x;
		}

		for( int i = 0; i <= radius; i++ ) sb.append(' ');
		sb.append('-');
		return sb.toString();
	}

	public BoardObject get( Point p ) {
		if( !objects.containsKey(p) ) return BoardObject.EMPTY;
		return BoardObject.valueOf(objects.get(p).toString());
	}

	@Nullable
	public Point find( BoardObject o ) {
		// TODO: Return the nearest one.
		for( Entry<Point, BoardObject> e : objects.entrySet() ) {
			if( o.equals(e.getValue()) ) return e.getKey();
		}
		return null;
	}

	@Nullable
	public Direction getDirection( Point p ) {
		if( p.getX() < 0 ) {
			if( p.getY() < 0 ) {
				return Direction.SOUTHWEST;
			} else if( p.getY() > 0 ) {
				return Direction.NORTHWEST;
			} else {
				return Direction.WEST;
			}
		} else if( p.getX() > 0 ) {
			if( p.getY() < 0 ) {
				return Direction.SOUTHEAST;
			} else if( p.getY() > 0 ) {
				return Direction.NORTHEAST;
			} else {
				return Direction.EAST;
			}
		} else {
			if( p.getY() < 0 ) {
				return Direction.SOUTH;
			} else if( p.getY() > 0 ) {
				return Direction.NORTH;
			} else {
				return null;
			}
		}
	}
}
