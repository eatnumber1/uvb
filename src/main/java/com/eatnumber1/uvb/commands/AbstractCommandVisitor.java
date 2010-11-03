package com.eatnumber1.uvb.commands;

import org.jetbrains.annotations.Nullable;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public abstract class AbstractCommandVisitor<T> implements CommandVisitor<T> {
	@Override
	@Nullable
	public T visitMoveCommand( MoveCommand command ) {
		return null;
	}

	@Override
	@Nullable
	public T visitSnowballCommand( MakeSnowballCommand command ) {
		return null;
	}
}
