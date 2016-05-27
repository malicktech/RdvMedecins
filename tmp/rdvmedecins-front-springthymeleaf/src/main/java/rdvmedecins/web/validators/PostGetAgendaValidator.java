package rdvmedecins.web.validators;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import rdvmedecins.springthymeleaf.server.requests.PostGetAgenda;
import rdvmedecins.springthymeleaf.server.requests.PostValiderRv;

public class PostGetAgendaValidator implements Validator {

	public PostGetAgendaValidator() {
	}

	@Override
	public boolean supports(Class<?> classe) {
		return PostGetAgenda.class.equals(classe) || PostValiderRv.class.equals(classe);
	}

	@Override
	public void validate(Object post, Errors errors) {
		// le jour choisi pour le rdv
		Date jour = null;
		if (post instanceof PostGetAgenda) {
			jour = ((PostGetAgenda) post).getJour();
		} else {
			if (post instanceof PostValiderRv) {
				jour = ((PostValiderRv) post).getJour();
			}
		}
		// on transforme les dates au format yyyy-MM-dd
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strJour = sdf.format(jour);
		String strToday = sdf.format(new Date());
		// le jour choisi ne doit pas précéder la date d'aujourd'hui
		if (strJour.compareTo(strToday) < 0) {
			errors.rejectValue("jour", "todayandafter.postChoixMedecinJour", null, null);
		}
	}

}
