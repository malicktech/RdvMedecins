package rdvmedecins.helpers;

import org.springframework.security.crypto.codec.Base64;

public class Base64Encoder {

	public static void main(String[] args) {
		// on attend deux arguments : login password
		if (args.length != 2) {
			System.out.println("Syntaxe : login password");
			System.exit(0);
		}
		// on récupère les deux arguments
		String chaîne = String.format("%s:%s", args[0], args[1]);
		// on encode la chaîne
		byte[] data = Base64.encode(chaîne.getBytes());
		// on affiche son encodage Base64
		System.out.println(new String(data));
	}

}
