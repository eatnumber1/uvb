package com.eatnumber1.uvb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public class RequestDispatcher {
	private static final Log log = LogFactory.getLog(RequestDispatcher.class);

	private Map<Request, RequestHandler> handlers = new HashMap<Request, RequestHandler>();

	public void addHandler( Request request, RequestHandler handler ) {
		handlers.put(request, handler);
	}

	public void dispatch( Request request, PrintWriter out, Scanner in ) {
		log.debug("Dispatching request " + request);
		handlers.get(request).run(out, in);
	}
}
