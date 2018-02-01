package br.inf.ids.rh.core.exceptions;

public class RestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RestException(String msg) {
		super(msg);
	}
	
	public RestException(Exception e) {
		super(e.getMessage());
	}
	
}
