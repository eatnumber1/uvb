package com.eatnumber1.uvb.commands;

import com.eatnumber1.uvb.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class MoveCommand extends AbstractCommand {
	private Direction direction;

	public MoveCommand( Direction direction ) {
		this.direction = direction;
	}

	@NotNull
	@Override
	protected Action getAction() {
		return Action.MOVE;
	}

	@Override
	protected int getValue() {
		return direction.getValue();
	}

	@Override
	public String toString() {
		return direction.toString();
	}

	@Override
	public <T> T visit( CommandVisitor<T> visitor ) {
		return visitor.visitMoveCommand(this);
	}

	public Direction getDirection() {
		return direction;
	}
}
