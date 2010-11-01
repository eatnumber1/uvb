package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Oct 31, 2010
 */
public class NopCommand implements Command {
	private static NopCommand instance = new NopCommand();

	private NopCommand() {
	}

	public static NopCommand getInstance() {
		return instance;
	}

	@Override
	public String serialize() {
		return String.valueOf(Action.NOP.getValue());
	}
}
