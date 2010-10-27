package com.eatnumber1.uvb;

/**
 * @author Russell Harmon
 * @since Oct 26, 2010
 */
public class UVBProtocolException extends RuntimeException {
    public UVBProtocolException() {
        super();
    }

    public UVBProtocolException( String s ) {
        super(s);
    }

    public UVBProtocolException( String s, Throwable throwable ) {
        super(s, throwable);
    }

    public UVBProtocolException( Throwable throwable ) {
        super(throwable);
    }
}
