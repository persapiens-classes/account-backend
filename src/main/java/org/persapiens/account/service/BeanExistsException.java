package org.persapiens.account.service;

public class BeanExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BeanExistsException(String message) {
		super(message);
	}

}
