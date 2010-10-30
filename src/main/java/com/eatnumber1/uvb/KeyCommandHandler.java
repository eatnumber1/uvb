package com.eatnumber1.uvb;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public class KeyCommandHandler implements CommandHandler {
	private String key;

	public KeyCommandHandler( String key ) {
		this.key = key;
	}

	@Override
	public void run( PrintWriter out, Scanner in ) {
		out.println(key);
		if( in.nextInt() != 1 ) throw new UVBProtocolException("Unauthorized by server");
		if( !"".equals(in.nextLine()) ) throw new UVBProtocolException("Garbage at end of payload");
	}
}
