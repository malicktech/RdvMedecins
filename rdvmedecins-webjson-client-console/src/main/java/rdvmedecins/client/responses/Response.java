package rdvmedecins.client.responses;

import java.util.List;

public class Response<T> {

	// ----------------- propriétés
	// statut de l'opération
	private int status;
	// les éventuels messages d'erreur
	private List<String> messages;
	// le corps de la réponse
	private T body;

	// constructeurs
	public Response() {

	}

	public Response(int status, List<String> messages, T body) {
		this.status = status;
		this.messages = messages;
		this.body = body;
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

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
