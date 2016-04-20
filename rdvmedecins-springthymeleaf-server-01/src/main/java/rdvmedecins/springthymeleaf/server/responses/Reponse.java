package rdvmedecins.springthymeleaf.server.responses;

public class Reponse {

	// ----------------- propriétés
	// statut de l'opération
	private int status;
	// la barre de navigation
	private String navbar;
	// le jumbotron
	private String jumbotron;
	// le corps de la page
	private String content;
	// l'agenda
	private String agenda;

	// ---------------constructeurs
	public Reponse() {
	}

	// ----------------------getters et setters
	public Reponse(int status, String navbar, String jumbotron, String content, String agenda) {
		this.status = status;
		this.navbar = navbar;
		this.jumbotron = jumbotron;
		this.content = content;
		this.agenda = agenda;
	}

	public String getJumbotron() {
		return jumbotron;
	}

	public void setJumbotron(String jumbotron) {
		this.jumbotron = jumbotron;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String html) {
		this.content = html;
	}

	public String getNavbar() {
		return navbar;
	}

	public void setNavbar(String navbar) {
		this.navbar = navbar;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}
}
