package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public enum Direction {
	NORTH(0),
	NORTHEAST(1),
	EAST(2),
	SOUTHEAST(3),
	SOUTH(4),
	SOUTHWEST(5),
	WEST(6),
	NORTHWEST(7);

	private int value;

	private Direction( int value ) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
