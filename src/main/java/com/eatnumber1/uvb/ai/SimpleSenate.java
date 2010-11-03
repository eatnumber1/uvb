package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.Command;
import com.eatnumber1.uvb.commands.MoveCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public class SimpleSenate implements Senate {
	private static Log log = LogFactory.getLog(SimpleSenate.class);

	private Set<Senator> senators;

	public SimpleSenate( Set<Senator> senators ) {
		this.senators = senators;
	}

	public SimpleSenate() {
		senators = new HashSet<Senator>();
	}

	@NotNull
	@Override
	public Command decide( GameMap map ) {
		log.debug("Senate is in session.");
		Set<Proposal> proposals = new HashSet<Proposal>();
		for( Senator senator : senators ) proposals.addAll(senator.propose(map));
		List<Ballot> ballots = new ArrayList<Ballot>(proposals.size());
		for( Proposal proposal : proposals ) {
			Ballot ballot = new SimpleBallot(proposal);
			for( Senator senator : senators ) senator.vote(map, ballot);
			ballots.add(ballot);
		}
		Collections.sort(ballots, new Comparator<Ballot>() {
			@Override
			public int compare( Ballot ballot, final Ballot ballot1 ) {
				Integer cmp = ballot.compareTo(ballot1);
				if( cmp != 0 ) return cmp;
				cmp = ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Integer>() {
					@Override
					@Nullable
					public Integer visitMoveCommand( final MoveCommand command ) {
						return ballot1.getProposal().getCommand().visit(new AbstractCommandVisitor<Integer>() {
							@Override
							@Nullable
							public Integer visitMoveCommand( MoveCommand command1 ) {
								return command.getDirection().compareTo(command1.getDirection());
							}
						});
					}
				});
				if( cmp == null ) return 0;
				return cmp;
			}
		});
		log.trace("Voting complete. Results are: " + ballots);
		Ballot acceptedBallot = ballots.get(ballots.size() - 1);
		log.debug("Ballot " + acceptedBallot + " wins.");
		return acceptedBallot.getProposal().getCommand();
	}

	@Override
	public void addSenator( Senator senator ) {
		senators.add(senator);
	}
}
