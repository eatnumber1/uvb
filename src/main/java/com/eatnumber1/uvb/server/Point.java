package com.eatnumber1.uvb.server;

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
}
