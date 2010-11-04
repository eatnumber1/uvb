package com.eatnumber1.uvb.commands;

import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Nov 3, 2010
 */
public abstract class AbstractCommand implements Command {
	@NotNull
	protected abstract Action getAction();

	protected abstract int getValue();

	@Override
	public String serialize() {
		return getAction().getValue() + ":" + getValue();
	}

	@Override
	public int hashCode() {
		return serialize().hashCode();
	}

	@Override
	public boolean equals( Object o ) {
		return o != null && o instanceof Command && serialize().equals(((Command) o).serialize());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName()).append('{');
		sb.append("action=").append(getAction().toString());
		sb.append(", value=").append(getValue());
		sb.append("}");
		return sb.toString();
	}
}
