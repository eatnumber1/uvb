package com.eatnumber1.uvb.commands;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public class MakeSnowballCommand implements Command {
	@Override
	public String serialize() {
		return "MAKESNOWBALL";
	}

	@Override
	public <T> T visit( CommandVisitor<T> visitor ) {
		return visitor.visitSnowballCommand(this);
	}
}
