package com.eatnumber1.uvb;

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
}
