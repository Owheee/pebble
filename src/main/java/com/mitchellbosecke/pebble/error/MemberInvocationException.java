package com.mitchellbosecke.pebble.error;

public class MemberInvocationException extends PebbleException{

	private static final long serialVersionUID = 5171728649340414187L;

	public MemberInvocationException(Throwable cause, String message) {
		super(cause, message);
	}

}
