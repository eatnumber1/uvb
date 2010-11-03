package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.MakeSnowballCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public class ReloadSenator implements Senator {
	private static final int VOTE_EXPONENT = 1;

	@NotNull
	@Override
	public Set<Proposal> propose( GameMap map ) {
		return Collections.<Proposal>singleton(new SimpleProposal(new MakeSnowballCommand()));
	}

	@Override
	public void vote( GameMap map, Ballot ballot ) {
		@Nullable
		Integer vote = ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Integer>() {
			@Override
			@Nullable
			public Integer visitSnowballCommand( MakeSnowballCommand command ) {
				return 1;
			}
		});
		if( vote != null ) ballot.vote(vote);
	}
}
