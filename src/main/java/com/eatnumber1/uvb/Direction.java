package com.eatnumber1.uvb;

import com.eatnumber1.uvb.board.Point;
import org.jetbrains.annotations.Nullable;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public enum Direction {
	NORTH(0, new Point(0, -1)),
	NORTHEAST(1, new Point(1, -1)),
	EAST(2, new Point(1, 0)),
	SOUTHEAST(3, new Point(1, 1)),
	SOUTH(4, new Point(0, 1)),
	SOUTHWEST(5, new Point(-1, 1)),
	WEST(6, new Point(-1, 0)),
	NORTHWEST(7, new Point(-1, -1));

	private int value;
	private Point point;

	private Direction( int value, Point point ) {
		this.value = value;
		this.point = point;
	}

	public int getValue() {
		return value;
	}

	public Point getPoint() {
		return point;
	}

	@Nullable
	public static Direction valueOf( int value ) {
		for( Direction d : Direction.values() ) {
			if( d.value == value ) return d;
		}
		return null;
	}
}
