package com.eatnumber1.uvb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public class Server {
	private static Log log = LogFactory.getLog(Server.class);

	private PrintWriter out;
	private Scanner in;

	public Server( InputStream in, OutputStream out ) {
		this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out, Charset.forName("US-ASCII"))), true);
		this.in = new Scanner(new BufferedReader(new InputStreamReader(in, Charset.forName("US-ASCII"))));
	}

	public void write( CharSequence buf ) {
		log.trace("WRITE " + buf);
		out.println(buf);
	}

	public String read() {
		String data = in.nextLine();
		log.trace("READ " + data);
		return data;
	}

	public boolean await() {
		return in.hasNextLine();
	}
}
