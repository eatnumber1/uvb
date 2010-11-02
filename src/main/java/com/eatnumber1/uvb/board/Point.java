package com.eatnumber1.uvb.board;

import com.eatnumber1.uvb.Direction;

/**
 * @author Russell Harmon
 * @since 2:07:48 PM
 */
public class Point {
	private final int x;
	private final int y;

	public Point( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) return true;
		if( !(o instanceof Point) ) return false;
		Point point = (Point) o;
		return x == point.x && y == point.y;

	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	public Point getAdjacentPoint( Direction direction ) {
		switch( direction ) {
			case EAST:
				return new Point(x + 1, y);
			case NORTH:
				return new Point(x, y - 1);
			case SOUTH:
				return new Point(x, y + 1);
			case WEST:
				return new Point(x - 1, y);
			case NORTHEAST:
				return new Point(x + 1, y - 1);
			case NORTHWEST:
				return new Point(x - 1, y - 1);
			case SOUTHEAST:
				return new Point(x + 1, y + 1);
			case SOUTHWEST:
				return new Point(x - 1, y + 1);
		}
		throw new AssertionError();
	}
}
