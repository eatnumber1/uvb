package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public enum Action {
	MOVE(0),
	THROWSNOWBALL(1),
	SNOWMAN(2),
	MAKESNOWBALL(3),
	NOP(4);

	private int value;

	private Action( int value ) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
