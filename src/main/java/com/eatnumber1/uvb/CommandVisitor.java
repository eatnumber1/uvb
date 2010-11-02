package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public interface CommandVisitor {
	void visitMoveCommand( MoveCommand command );
}
