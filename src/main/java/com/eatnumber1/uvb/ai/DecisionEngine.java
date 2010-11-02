package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Command;
import com.eatnumber1.uvb.board.GameMap;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public interface DecisionEngine {
	@NotNull
	Command decide( GameMap map );
}
