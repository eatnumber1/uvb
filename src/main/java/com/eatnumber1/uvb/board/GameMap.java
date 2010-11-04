package com.eatnumber1.uvb.board;

import com.eatnumber1.uvb.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Russell Harmon
 * @since 1:55:39 PM
 */
public class GameMap {
	private Map<BoardObjectType, Character> objectTypes;
	private Map<Point, BoardObjectType> objects;
	private int radius;

	public GameMap( Map<BoardObjectType, Character> objectTypes, int radius, Map<Point, BoardObjectType> objects ) {
		this.objectTypes = objectTypes;
		this.radius = radius;
		this.objects = objects;

		/*
		for( Entry<Point, BoardObjectType> e : objects.entrySet() ) {
			long rowRadius = Math.round(Math.sqrt(Math.pow(radius, 2) - Math.pow(e.getKey().getY(), 2)));
			if( rowRadius > Integer.MAX_VALUE ) throw new IndexOutOfBoundsException("Number too big");
			if( e.getKey().getX() > rowRadius || e.getKey().getX() < -rowRadius ) {
				throw new IndexOutOfBoundsException("Board object " + e.getDirection() + " outside viewport at point " + e.getKey());
			}
		}
		*/
	}

	private char toChar( BoardObjectType obj ) {
		return objectTypes.get(obj);
	}

	private int rowRadius( int y ) {
		long rowRadius = Math.round(Math.sqrt(Math.pow(radius, 2) - Math.pow(y, 2)));
		if( rowRadius > Integer.MAX_VALUE ) throw new IndexOutOfBoundsException("Number too big");
		return (int) rowRadius;
	}

	private char getBorderChar( int curRadius, int prevRadius, int nextRadius, boolean left ) {
		char open, close;
		if( left ) {
			open = '/';
			close = '\\';
		} else {
			open = '\\';
			close = '/';
		}
		if( prevRadius == curRadius && nextRadius > curRadius ) {
			return '|';
		} else if( prevRadius > curRadius && nextRadius == curRadius ) {
			return '|';
		} else if( prevRadius > curRadius || nextRadius < curRadius ) {
			return close;
		} else if( prevRadius < curRadius || nextRadius > curRadius ) {
			return open;
		} else {
			return '|';
		}
	}

	public String render() {
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i <= radius; i++ ) sb.append(' ');
		sb.append("-\n");

		int last = Integer.MIN_VALUE;
		int next = rowRadius(-radius);
		for( int y = -radius; y <= radius; y++ ) {
			int rowRadius = next;
			next = y == radius ? Integer.MAX_VALUE : rowRadius(y + 1);
			for( int i = 0; i < radius - rowRadius; i++ ) sb.append(' ');
			sb.append(getBorderChar(rowRadius, last, next, true));
			for( int x = -rowRadius; x <= rowRadius; x++ ) {
				Point point = new Point(x, y);
				sb.append(toChar(objects.containsKey(point) ? objects.get(point) : BoardObjectType.EMPTY));
			}
			sb.append(getBorderChar(rowRadius, last, next, false)).append('\n');
			last = rowRadius;
		}

		for( int i = 0; i <= radius; i++ ) sb.append(' ');
		sb.append('-');
		return sb.toString();
	}

	public BoardObjectType get( Point p ) {
		if( !objects.containsKey(p) ) return BoardObjectType.EMPTY;
		return BoardObjectType.valueOf(objects.get(p).toString());
	}

	@Nullable
	public Point find( BoardObjectType o ) {
		// TODO: Return the nearest one.
		for( Entry<Point, BoardObjectType> e : objects.entrySet() ) {
			if( o.equals(e.getValue()) ) return e.getKey();
		}
		return null;
	}

	@Nullable
	public Direction getDirection( Point p ) {
		if( p.getX() < 0 ) {
			if( p.getY() < 0 ) {
				return Direction.NORTHWEST;
			} else if( p.getY() > 0 ) {
				return Direction.SOUTHWEST;
			} else {
				return Direction.WEST;
			}
		} else if( p.getX() > 0 ) {
			if( p.getY() < 0 ) {
				return Direction.NORTHEAST;
			} else if( p.getY() > 0 ) {
				return Direction.SOUTHEAST;
			} else {
				return Direction.EAST;
			}
		} else {
			if( p.getY() < 0 ) {
				return Direction.NORTH;
			} else if( p.getY() > 0 ) {
				return Direction.SOUTH;
			} else {
				return null;
			}
		}
	}

	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("GameMap");
		sb.append("{objectTypes=").append(objectTypes);
		sb.append(", objects=").append(objects);
		sb.append(", radius=").append(radius);
		sb.append('}');
		return sb.toString();
	}

	public int getRadius() {
		return radius;
	}

	public Point getMyPosition() {
		return new Point(0, 0);
	}
}
