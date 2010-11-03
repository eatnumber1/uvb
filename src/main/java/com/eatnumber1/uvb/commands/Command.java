package com.eatnumber1.uvb.commands;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public interface Command {
	String serialize();

	<T> T visit( CommandVisitor<T> visitor );
}
