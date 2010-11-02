package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.Command;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public class SimpleCommandProposal implements CommandProposal {
	private Command command;

	public SimpleCommandProposal( Command command ) {
		this.command = command;
	}

	@Override
	public Command getCommand() {
		return command;
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) return true;
		if( !(o instanceof SimpleCommandProposal) ) return false;
		SimpleCommandProposal that = (SimpleCommandProposal) o;
		return !(command != null ? !command.equals(that.command) : that.command != null);

	}

	@Override
	public int hashCode() {
		return command != null ? command.hashCode() : 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("SimpleCommandProposal");
		sb.append("{command=").append(command);
		sb.append('}');
		return sb.toString();
	}
}
