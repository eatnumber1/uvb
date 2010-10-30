package com.eatnumber1.uvb;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public interface CommandHandler {
	void run( PrintWriter out, Scanner in );
}
