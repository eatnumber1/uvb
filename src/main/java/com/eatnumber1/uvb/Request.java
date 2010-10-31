package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public enum Request {
	KEY("key"),
	MOVE("move");

	private String value;

	private Request( String value ) {
		this.value = value;
	}

	public static Request getCommand( String value ) {
		for( Request c : Request.values() ) if( c.value.equals(value) ) return c;
		throw new IllegalArgumentException("No command found for value " + value);
	}
}
