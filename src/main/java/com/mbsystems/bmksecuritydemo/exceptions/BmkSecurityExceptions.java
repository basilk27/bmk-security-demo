package com.mbsystems.bmksecuritydemo.exceptions;

public class BmkSecurityExceptions extends RuntimeException {

    public BmkSecurityExceptions() {
    }

    public BmkSecurityExceptions( String message ) {
        super( message );
    }

    public BmkSecurityExceptions( String message, Throwable cause ) {
        super( message, cause );
    }

    public BmkSecurityExceptions( Throwable cause ) {
        super( cause );
    }
}
