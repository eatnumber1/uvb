package com.eatnumber1.uvb.ai;

import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public interface CommandBallot extends Comparable<CommandBallot> {
	@NotNull
	CommandProposal getProposal();

	void vote( int value );

	int tally();
}
