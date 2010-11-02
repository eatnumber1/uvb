package com.eatnumber1.uvb;

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
	public void run( Server server ) {
		server.write(key);
		if( Integer.valueOf(server.read()) != 1 ) throw new ProtocolException("Unauthorized by server");
	}
}
