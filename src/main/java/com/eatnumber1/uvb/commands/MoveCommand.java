package com.eatnumber1.uvb.commands;

import com.eatnumber1.uvb.Action;
import com.eatnumber1.uvb.Direction;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class MoveCommand implements Command {
	private Direction direction;

	public MoveCommand( Direction direction ) {
		this.direction = direction;
	}

	@Override
	public String serialize() {
		return Action.MOVE.getValue() + ":" + direction.getValue();
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) return true;
		if( !(o instanceof MoveCommand) ) return false;
		MoveCommand that = (MoveCommand) o;
		return direction == that.direction;

	}

	@Override
	public int hashCode() {
		return direction != null ? direction.hashCode() : 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("MoveCommand");
		sb.append("{direction=").append(direction);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public <T> T visit( CommandVisitor<T> visitor ) {
		return visitor.visitMoveCommand(this);
	}

	public Direction getDirection() {
		return direction;
	}
}
