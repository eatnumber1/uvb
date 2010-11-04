package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.board.BoardObjectType;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.Point;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.MoveCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public class CollisionAvoidanceSenator extends AbstractSenator {
	private static Log log = LogFactory.getLog(CollisionAvoidanceSenator.class);

	private static final int VOTE = -1000000;

	private BoardObjectType object;

	public CollisionAvoidanceSenator( BoardObjectType object ) {
		this.object = object;
	}

	@Override
	public void vote( @NotNull final GameMap map, @NotNull final Ballot ballot ) {
		ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			@Nullable
			public Void visitMoveCommand( MoveCommand command ) {
				Point p = map.getMyPosition();
				p = p.getAdjacentPoint(command.getDirection());
				if( object.equals(map.get(p)) ) {
					log.debug("Voting " + VOTE + " on " + ballot + " to avoid a collision with a " + object + " at " + p);
					ballot.vote(VOTE);
				}
				return null;
			}
		});
	}
}
