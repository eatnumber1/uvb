package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.commands.MoveCommand;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class MoveEverywhereSenator implements Senator {
	@NotNull
	@Override
	public Set<Proposal> propose( GameMap map ) {
		Set<Proposal> proposals = new HashSet<Proposal>();
		proposals.add(new SimpleProposal(new MoveCommand(Direction.NORTH)));
		proposals.add(new SimpleProposal(new MoveCommand(Direction.SOUTH)));
		proposals.add(new SimpleProposal(new MoveCommand(Direction.EAST)));
		proposals.add(new SimpleProposal(new MoveCommand(Direction.WEST)));
		proposals.add(new SimpleProposal(new MoveCommand(Direction.NORTHEAST)));
		proposals.add(new SimpleProposal(new MoveCommand(Direction.SOUTHEAST)));
		proposals.add(new SimpleProposal(new MoveCommand(Direction.NORTHWEST)));
		proposals.add(new SimpleProposal(new MoveCommand(Direction.SOUTHWEST)));
		return proposals;
	}

	@Override
	public void vote( GameMap map, Ballot ballot ) {
	}
}
