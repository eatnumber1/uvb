package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Command;
import com.eatnumber1.uvb.board.GameMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public class SimpleCommandSenate implements CommandSenate {
	private static Log log = LogFactory.getLog(SimpleCommandSenate.class);

	private Set<CommandSenator> senators;

	public SimpleCommandSenate( Set<CommandSenator> senators ) {
		this.senators = senators;
	}

	public SimpleCommandSenate() {
		senators = new HashSet<CommandSenator>();
	}

	@NotNull
	@Override
	public Command decide( GameMap map ) {
		log.debug("Senate is in session.");
		Set<CommandProposal> proposals = new HashSet<CommandProposal>();
		for( CommandSenator senator : senators ) proposals.addAll(senator.propose(map));
		List<CommandBallot> ballots = new ArrayList<CommandBallot>(proposals.size());
		for( CommandProposal proposal : proposals ) {
			CommandBallot ballot = new SimpleCommandBallot(proposal);
			for( CommandSenator senator : senators ) senator.vote(map, ballot);
			ballots.add(ballot);
		}
		Collections.sort(ballots);
		log.trace("Voting complete. Results are: " + ballots);
		CommandBallot acceptedBallot = ballots.get(ballots.size() - 1);
		log.debug("Ballot " + acceptedBallot + " wins.");
		return acceptedBallot.getProposal().getCommand();
	}

	@Override
	public void addSenator( CommandSenator senator ) {
		senators.add(senator);
	}
}
