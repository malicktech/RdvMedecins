package rdvmedecins.client.dao;

import java.util.List;

public class RdvMedecinsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	// code d'erreur
	private int status;
	// liste de messages d'erreur
	private List<String> messages;

	public RdvMedecinsException() {
	}

	public RdvMedecinsException(int code, List<String> messages) {
		super();
		this.status = code;
		this.messages = messages;
	}

	// getters et setters
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
