package de.unicorn.model;
/**
 * Bietet eine Reihe statischer Methoden an, mit welchen die syntaktische Korrektheit von
 * Strings Ã¼berprÃ¼ft werden kann.
 * @author fabian
 *
 */
public class SyntaxChecker {

	/**
	 *  ÃœberprÃ¼ft, ob ein String in einen Integerwert umgewandelt werden kann.
	 * @param ref
	 * @return
	 */
	public static boolean isNummeric(String ref) {
		try {
			Integer.parseInt(ref);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Überprüft, ob s eine gültige Portnummer ist.
	 * @param s übergebene Portnummer als String
	 * @return ob s eine gültige Portnummer ist.
	 */
	public static boolean isPortNumber(String s)
	{
		if(isNummeric(s) && Integer.parseInt(s) > 1023 && Integer.parseInt(s) < 65536)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 *  ÃœberprÃ¼ft, ob der angegebende String eine IP Adresse bildet.
	 * @param ref String, welcher zum Ã¼berprÃ¼fen Ã¼bergeben wird.
	 * @return gibt zurÃ¼ck, ob ein ref eine syntaktisch korrekte IP Adresse ist.
	 */
	public static boolean isWellFormedIpAddress(String ref) {
		
		String [] b = ref.split("\\.");
		if (b.length == 4) {
			
			for(int i = 0; i < 4; i++) {
				if (!isNummeric(b[i]) && (Integer.parseInt(b[i]) > 255 || Integer.parseInt(b[i]) < 0)) {
					return false;
				}
			}
			
			return true;
			
		}else {
			
			return false;
			
		}	
	}
	/**
	 * ÃœberprÃ¼ft, ob der SessionName keine Leerzeichen oder ZeilenumbrÃ¼che enthält.
	 * @param ref String, der Ã¼berprÃ¼ft werden soll
	 * @return Gibt zurÃ¼ck, ob ref ein gÃ¼ltiger SessionName ist.
	 */
	public static boolean isWellFormedSessionName(String ref) {
		return !(ref.contains(" ") || ref.contains("\n") || ref.equals(""));
	}
	/**
	 * Überprüft, ob eine Nachricht keine Zeilenumbrüche enthält.
	 * @param m die zu sendende Nachricht
	 * @return ob m eine gültige Nachricht ist.
	 */
	public static boolean isWellFormedMessage(String m)
	{
		return !(m.contains("\n"));
	}
		
	
}
