package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public interface Command {
	String serialize();

	void visit( CommandVisitor visitor );
}
