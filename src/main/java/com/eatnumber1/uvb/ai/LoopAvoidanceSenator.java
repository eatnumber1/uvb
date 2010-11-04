package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.Point;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.MoveCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public class LoopAvoidanceSenator extends AbstractSenator {
	private static Log log = LogFactory.getLog(LoopAvoidanceSenator.class);

	private static final int MAX_POINTS = 100;
	private static final int VOTE = -1;

	private List<Point> visited = new LinkedList<Point>();

	@Override
	public void vote( GameMap map, final Ballot ballot ) {
		ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			public Void visitMoveCommand( MoveCommand command ) {
				for( Point p : visited ) {
					if( p.equals(command.getDirection().getPoint()) ) {
						log.debug("Voting " + VOTE + " to avoid re-visiting point " + p);
						ballot.vote(VOTE);
						return null;
					}
				}
				return null;
			}
		});
	}

	@Override
	public void results( Proposal winner ) {
		winner.getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			public Void visitMoveCommand( MoveCommand command ) {
				if( visited.size() == MAX_POINTS ) visited.remove(0);
				visited.add(command.getDirection().getPoint());
				return null;
			}
		});
	}
}
