package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Direction;
import com.eatnumber1.uvb.board.BoardObjectType;
import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.board.Point;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.MakeSnowballCommand;
import com.eatnumber1.uvb.commands.ThrowSnowballCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 4, 2010
 */
public class ThrowSnowballSenator extends AbstractSenator {
	@NotNull
	private static Log log = LogFactory.getLog(ThrowSnowballSenator.class);

	private static final int VOTE = 100000;
	private static final int MAX_DISTANCE = 3;

	// TODO: Track game state in a shared object.
	private int snowballs;

	@NotNull
	private BoardObjectType object;

	@NotNull
	private Set<Proposal> proposals = new HashSet<Proposal>();

	public ThrowSnowballSenator( @NotNull BoardObjectType object ) {
		this.object = object;
	}

	@NotNull
	@Override
	public Set<Proposal> propose( @NotNull GameMap map ) {
		for( Direction d : Direction.values() ) {
			Point p = map.getMyPosition();
			for( int i = 0; i < MAX_DISTANCE; i++ ) {
				p = p.getAdjacentPoint(d);
				if( map.get(p).equals(object) ) {
					if( snowballs == 0 ) {
						log.warn("Would throw a snowball " + d + ", but we are out!");
						return Collections.emptySet();
					}
					log.debug("Proposing we throw a snowball " + d + " at the " + object + " at " + p);
					proposals.add(new SimpleProposal(new ThrowSnowballCommand(d)));
				}
			}
		}
		return Collections.unmodifiableSet(proposals);
	}

	@Override
	public void vote( @NotNull GameMap map, @NotNull Ballot ballot ) {
		for( Proposal p : proposals ) {
			if( ballot.getProposal().equals(p) ) {
				log.debug("Voting " + VOTE + " for " + ballot);
				ballot.vote(VOTE);
				break;
			}
		}
	}

	@Override
	public void results( @NotNull Proposal winner ) {
		winner.getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			public Void visitMakeSnowballCommand( MakeSnowballCommand command ) {
				snowballs++;
				return null;
			}

			@Override
			public Void visitThrowSnowballCommand( ThrowSnowballCommand command ) {
				snowballs--;
				return null;
			}
		});
		proposals.clear();
	}
}
