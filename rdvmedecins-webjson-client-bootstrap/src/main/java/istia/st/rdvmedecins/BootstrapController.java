package istia.st.rdvmedecins;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BootstrapController {

	@RequestMapping(value = "/bs-01", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bso1() {
		return "bs-01";
	}

	@RequestMapping(value = "/bs-02", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs02() {
		return "bs-02";
	}

	@RequestMapping(value = "/bs-03", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs03() {
		return "bs-03";
	}

	@RequestMapping(value = "/bs-04", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs04() {
		return "bs-04";
	}

	@RequestMapping(value = "/bs-05", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs05() {
		return "bs-05";
	}

	@RequestMapping(value = "/bs-06", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs06() {
		return "bs-06";
	}

	@RequestMapping(value = "/bs-07", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs07() {
		return "bs-07";
	}

	@RequestMapping(value = "/bs-08", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs08() {
		return "bs-08";
	}

	@RequestMapping(value = "/bs-09", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
	public String bs09() {
		return "bs-09";
	}
}
