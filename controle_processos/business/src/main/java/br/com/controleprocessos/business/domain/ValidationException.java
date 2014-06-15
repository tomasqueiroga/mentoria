package br.com.controleprocessos.business.domain;

import java.util.Arrays;
import java.util.List;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -2996566651098006715L;

	private List<String> messages;

	public ValidationException(String message) {
		setMessages(Arrays.asList(message));
	}

	public ValidationException(String... messages) {
		setMessages(Arrays.asList(messages));
	}

	public ValidationException(List<String> messages) {
		setMessages(messages);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
