package com.vendingMachine.exceptions;

import com.vendingMachine.util.ConstantUtil;
/**
 * Exception class when no sufficient change is available in the cash inventory
 * @author Hussain
 * @version 1.0
 */
public class NoSufficientChangeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message = ConstantUtil.msg_NO_SUFFICENT_CHANGE_EXCEPTION;

	@Override
	public String getMessage() {
		return message;
	}

}
