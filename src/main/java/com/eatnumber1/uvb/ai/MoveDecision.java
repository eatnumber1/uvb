package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Command;
import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.MoveCommand;
import com.eatnumber1.uvb.board.GameMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class MoveDecision implements DecisionEngine {
	private static Log log = LogFactory.getLog(MoveDecision.class);

	@Override
	public Command decide( GameMap map ) {
		Direction direction = Direction.SOUTH;
		log.debug("Moving " + direction);
		return new MoveCommand(direction);
	}
}
