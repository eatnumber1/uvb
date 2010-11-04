package com.eatnumber1.uvb.commands;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public interface CommandVisitor<T> {
	T visitMoveCommand( MoveCommand command );

	T visitThrowSnowballCommand( ThrowSnowballCommand command );

	T visitMakeSnowballCommand( MakeSnowballCommand command );
}
