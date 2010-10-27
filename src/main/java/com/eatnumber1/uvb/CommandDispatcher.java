package com.eatnumber1.uvb;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public class CommandDispatcher {
    private Map<Command, CommandHandler> handlers = new HashMap<Command, CommandHandler>();

    public void addHandler( Command command, CommandHandler handler ) {
        handlers.put(command, handler);
    }

    public void dispatch( Command command, PrintWriter out, Scanner in ) {
        handlers.get(command).run(out, in);
    }
}
