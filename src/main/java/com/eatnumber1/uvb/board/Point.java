package com.eatnumber1.uvb.board;

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
}
