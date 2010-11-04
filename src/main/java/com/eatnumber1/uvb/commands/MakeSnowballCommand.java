package com.eatnumber1.uvb.commands;

import com.eatnumber1.uvb.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public class MakeSnowballCommand extends AbstractCommand {
	@NotNull
	@Override
	protected Action getAction() {
		return Action.MAKESNOWBALL;
	}

	@NotNull
	@Override
	protected Direction getDirection() {
		Direction d = Direction.valueOf(0);
		assert (d != null);
		return d;
	}

	@Override
	public <T> T visit( CommandVisitor<T> visitor ) {
		return visitor.visitMakeSnowballCommand(this);
	}

	@Override
	public String toString() {
		return Action.MAKESNOWBALL.toString();
	}
}
