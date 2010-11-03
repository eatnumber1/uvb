package com.eatnumber1.uvb.ai;

import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public interface Ballot extends Comparable<Ballot> {
	@NotNull
	Proposal getProposal();

	void vote( int value );

	int tally();
}
