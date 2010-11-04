package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.board.GameMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public abstract class AbstractSenator implements Senator {
	@NotNull
	@Override
	public Set<Proposal> propose( @NotNull GameMap map ) {
		return Collections.emptySet();
	}

	@Override
	public void vote( @NotNull GameMap map, @NotNull Ballot ballot ) {
	}

	@Override
	public void results( @NotNull Proposal winner ) {
	}
}
