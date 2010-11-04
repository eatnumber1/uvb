package com.eatnumber1.uvb.commands;

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

	@Override
	protected int getValue() {
		return 0;
	}

	@Override
	public <T> T visit( CommandVisitor<T> visitor ) {
		return visitor.visitSnowballCommand(this);
	}

	@Override
	public String toString() {
		return Action.MAKESNOWBALL.toString();
	}
}
