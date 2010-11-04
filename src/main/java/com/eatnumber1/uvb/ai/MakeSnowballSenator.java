package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.MakeSnowballCommand;
import com.eatnumber1.uvb.commands.ThrowSnowballCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public class MakeSnowballSenator extends AbstractSenator {
	private static Log log = LogFactory.getLog(MakeSnowballSenator.class);

	private static int SNOWBALLS = 10;

	private int snowballs;

	@NotNull
	@Override
	public Set<Proposal> propose( @NotNull GameMap map ) {
		return Collections.<Proposal>singleton(new SimpleProposal(new MakeSnowballCommand()));
	}

	@Override
	public void vote( @NotNull GameMap map, @NotNull final Ballot ballot ) {
		ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			@Nullable
			public Void visitMakeSnowballCommand( MakeSnowballCommand command ) {
				double v = (Math.pow(snowballs, 2) * -1) + Math.pow(SNOWBALLS, 2);
				if( v > Integer.MAX_VALUE ) throw new IndexOutOfBoundsException("Number too big");
				int vote = (int) v;
				log.debug("Voting " + vote + " to make a snowball because we currently have " + snowballs);
				ballot.vote(vote);
				return null;
			}
		});
	}

	@Override
	public void results( @NotNull Proposal winner ) {
		winner.getCommand().visit(new AbstractCommandVisitor<Void>() {
			@Override
			@Nullable
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
	}
}
