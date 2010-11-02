package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.MoveCommand;
import com.eatnumber1.uvb.board.GameMap;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class MoveEverywhereSenator implements CommandSenator {
	@NotNull
	@Override
	public Set<CommandProposal> propose( GameMap map ) {
		Set<CommandProposal> proposals = new HashSet<CommandProposal>();
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.NORTH)));
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.SOUTH)));
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.EAST)));
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.WEST)));
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.NORTHEAST)));
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.SOUTHEAST)));
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.NORTHWEST)));
		proposals.add(new SimpleCommandProposal(new MoveCommand(Direction.SOUTHWEST)));
		return proposals;
	}

	@Override
	public void vote( GameMap map, CommandBallot ballot ) {
	}
}
