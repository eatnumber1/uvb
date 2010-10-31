package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Command;
import com.eatnumber1.uvb.board.GameMap;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public interface DecisionEngine {
	Command decide( GameMap map );
}
