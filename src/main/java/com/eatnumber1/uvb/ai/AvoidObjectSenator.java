package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.CommandVisitor;
import com.eatnumber1.uvb.MoveCommand;
import com.eatnumber1.uvb.board.BoardObjectType;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.Point;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public class AvoidObjectSenator implements CommandSenator {
	private static Log log = LogFactory.getLog(AvoidObjectSenator.class);

	private BoardObjectType object;

	public AvoidObjectSenator( BoardObjectType object ) {
		this.object = object;
	}

	@NotNull
	@Override
	public Set<CommandProposal> propose( GameMap map ) {
		//noinspection unchecked
		return Collections.EMPTY_SET;
	}

	@Override
	public void vote( final GameMap map, final CommandBallot ballot ) {
		ballot.getProposal().getCommand().visit(new CommandVisitor() {
			@Override
			public void visitMoveCommand( MoveCommand command ) {
				Point p = map.getMyPosition();
				for( int i = 0; i < map.getRadius(); i++ ) {
					p = p.getAdjacentPoint(command.getDirection());
					if( object.equals(map.get(p)) ) {
						log.debug("Voting -1 on " + ballot + " to avoid the " + object + " at " + p);
						// TODO: More insistent if trees are nearer.
						ballot.vote(-1);
						return;
					}
				}
			}
		});
	}
}
