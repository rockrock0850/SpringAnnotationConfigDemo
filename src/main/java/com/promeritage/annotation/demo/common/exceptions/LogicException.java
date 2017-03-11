package com.promeritage.annotation.demo.common.exceptions;

import com.promeritage.annotation.demo.common.utils.Constant.Status;

public class LogicException extends Exception {

	private static final long serialVersionUID = 8099075923439417456L;

	private String code;
	private String message;

	public LogicException(Status status) {
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}