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
public class MoveToSenator extends AbstractSenator {
	private static Log log = LogFactory.getLog(MoveToSenator.class);

	private static final int VOTE = 50;

	@Nullable
	private Proposal proposal;

	@NotNull
	private BoardObjectType object;

	public MoveToSenator( @NotNull BoardObjectType object ) {
		this.object = object;
	}

	@NotNull
	@Override
	public Set<Proposal> propose( @NotNull GameMap map ) {
		Point enemy = map.find(object);
		if( enemy == null ) return Collections.emptySet();
		Direction direction = map.getDirection(enemy);
		log.debug("Proposing we move " + direction + " to engage");
		proposal = new SimpleProposal(new MoveCommand(direction));
		return Collections.singleton(proposal);
	}

	@Override
	public void vote( @NotNull GameMap map, @NotNull Ballot ballot ) {
		if( proposal == null ) return;
		if( ballot.getProposal().equals(proposal) ) {
			log.debug("Voting " + VOTE + " on " + ballot);
			ballot.vote(VOTE);
			proposal = null;
		}
	}
}
