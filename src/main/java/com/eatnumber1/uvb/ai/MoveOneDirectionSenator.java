package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.MoveCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Russell Harmon
 * @since Nov 4, 2010
 */
public class MoveOneDirectionSenator extends AbstractSenator {
	private static Log log = LogFactory.getLog(MoveOneDirectionSenator.class);

	private static final int VOTE = 5;

	@Nullable
	private Direction direction;

	@Override
	public void vote( @NotNull GameMap map, @NotNull final Ballot ballot ) {
		if( direction == null ) return;
		ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			public Void visitMoveCommand( MoveCommand command ) {
				if( command.getDirection().equals(direction) ) {
					log.debug("Voting " + VOTE + " we keep moving " + direction);
					ballot.vote(VOTE);
				}
				return null;
			}
		});
	}

	@Override
	public void results( @NotNull Proposal winner ) {
		winner.getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			public Void visitMoveCommand( MoveCommand command ) {
				direction = command.getDirection();
				return null;
			}
		});
	}
}
