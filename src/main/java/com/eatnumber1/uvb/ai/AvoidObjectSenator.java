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

import java.util.Collections;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public class AvoidObjectSenator implements Senator {
	private static Log log = LogFactory.getLog(AvoidObjectSenator.class);

	private static final int EXPONENT = 1;

	private BoardObjectType object;

	public AvoidObjectSenator( BoardObjectType object ) {
		this.object = object;
	}

	@NotNull
	@Override
	public Set<Proposal> propose( GameMap map ) {
		return Collections.emptySet();
	}

	@Override
	public void vote( final GameMap map, final Ballot ballot ) {
		ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			@Nullable
			public Void visitMoveCommand( MoveCommand command ) {
				Point p = map.getMyPosition();
				for( int i = 0; i < map.getRadius(); i++ ) {
					p = p.getAdjacentPoint(command.getDirection());
					if( object.equals(map.get(p)) ) {
						@SuppressWarnings({ "PointlessArithmeticExpression" })
						int vote = ((map.getRadius() - i) * -1) * EXPONENT;
						log.debug("Voting " + vote + " on " + ballot + " to avoid the " + object + " at " + p);
						ballot.vote(vote);
						return null;
					}
				}
				return null;
			}
		});
	}
}
