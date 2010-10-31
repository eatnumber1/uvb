package com.eatnumber1.uvb;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public class KeyRequestHandler implements RequestHandler {
	private String key;

	public KeyRequestHandler( String key ) {
		this.key = key;
	}

	@Override
	public void run( PrintWriter out, Scanner in ) {
		out.println(key);
		if( in.nextInt() != 1 ) throw new ProtocolException("Unauthorized by server");
		if( !"".equals(in.nextLine()) ) throw new ProtocolException("Garbage at end of payload");
	}
}
