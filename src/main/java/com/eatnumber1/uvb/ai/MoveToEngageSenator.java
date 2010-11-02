package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.MoveCommand;
import com.eatnumber1.uvb.board.BoardObjectType;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.Point;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class MoveToEngageSenator implements CommandSenator {
	private static Log log = LogFactory.getLog(MoveToEngageSenator.class);

	@Nullable
	private CommandProposal engageProposal;

	@NotNull
	@Override
	public Set<CommandProposal> propose( GameMap map ) {
		Point enemy = map.find(BoardObjectType.PLAYER);
		if( enemy == null ) {
			log.debug("No enemy found. No proposition made.");
			//noinspection unchecked
			return Collections.EMPTY_SET;
		}
		Direction direction = map.getDirection(enemy);
		log.debug("Proposing we move " + direction + " to engage");
		engageProposal = new SimpleCommandProposal(new MoveCommand(direction));
		return Collections.singleton(engageProposal);
	}

	@Override
	public void vote( GameMap map, CommandBallot ballot ) {
		if( engageProposal == null ) return;
		if( ballot.getProposal().equals(engageProposal) ) ballot.vote(10);
	}
}
