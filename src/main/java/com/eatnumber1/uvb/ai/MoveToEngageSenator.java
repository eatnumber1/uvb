package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.board.BoardObjectType;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.Point;
import com.eatnumber1.uvb.commands.MoveCommand;
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
public class MoveToEngageSenator extends AbstractSenator {
	private static Log log = LogFactory.getLog(MoveToEngageSenator.class);

	private static final int VOTE = 100;

	@Nullable
	private Proposal engageProposal;

	@NotNull
	@Override
	public Set<Proposal> propose( GameMap map ) {
		Point enemy = map.find(BoardObjectType.PLAYER);
		if( enemy == null ) return Collections.emptySet();
		Direction direction = map.getDirection(enemy);
		log.debug("Proposing we move " + direction + " to engage");
		engageProposal = new SimpleProposal(new MoveCommand(direction));
		return Collections.singleton(engageProposal);
	}

	@Override
	public void vote( GameMap map, Ballot ballot ) {
		if( engageProposal == null ) return;
		if( ballot.getProposal().equals(engageProposal) ) {
			log.debug("Voting " + VOTE + " on " + ballot);
			ballot.vote(VOTE);
			engageProposal = null;
		}
	}
}
