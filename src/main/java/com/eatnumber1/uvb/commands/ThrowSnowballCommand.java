package com.eatnumber1.uvb.commands;

import com.eatnumber1.uvb.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Nov 4, 2010
 */
public class ThrowSnowballCommand extends AbstractCommand {
	@NotNull
	private Direction direction;

	public ThrowSnowballCommand( @NotNull Direction direction ) {
		this.direction = direction;
	}

	@NotNull
	@Override
	protected Action getAction() {
		return Action.THROWSNOWBALL;
	}

	@NotNull
	@Override
	protected Direction getDirection() {
		return direction;
	}

	@Override
	public <T> T visit( @NotNull CommandVisitor<T> visitor ) {
		return visitor.visitThrowSnowballCommand(this);
	}
}
