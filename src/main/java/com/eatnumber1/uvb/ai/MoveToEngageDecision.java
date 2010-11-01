package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Command;
import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.MoveCommand;
import com.eatnumber1.uvb.board.BoardObject;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.Point;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class MoveToEngageDecision implements DecisionEngine {
	private static Log log = LogFactory.getLog(MoveToEngageDecision.class);

	@Override
	public Command decide( GameMap map ) {
		Point enemy = map.find(BoardObject.PLAYER);
		if( enemy == null ) {
			log.debug("No enemy found");
			return null;
		}
		Direction direction = map.getDirection(enemy);
		log.debug("Moving " + direction + " to engage");
		return new MoveCommand(direction);
	}
}
