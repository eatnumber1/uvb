package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Command;
import com.eatnumber1.uvb.board.GameMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class DecisionTree implements DecisionEngine {
	private List<DecisionEngine> children = new ArrayList<DecisionEngine>();

	public void addChild( DecisionEngine engine ) {
		children.add(engine);
	}

	@Override
	public Command decide( GameMap map ) {
		for( DecisionEngine engine : children ) {
			Command command = engine.decide(map);
			if( command != null ) return command;
		}
		return null;
	}
}
