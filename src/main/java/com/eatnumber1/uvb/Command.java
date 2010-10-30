package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public enum Command {
	KEY("key"),
	MOVE("move");

	private String value;

	private Command( String value ) {
		this.value = value;
	}

	public static Command getCommand( String value ) {
		for( Command c : Command.values() ) if( c.value.equals(value) ) return c;
		throw new IllegalArgumentException("No command found for value " + value);
	}
}
