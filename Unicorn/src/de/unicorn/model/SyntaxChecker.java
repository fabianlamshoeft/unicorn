package de.unicorn.model;
/**
 * Bietet eine Reihe statischer Methoden an, mit welchen die syntaktische Korrektheit von
 * Strings überprüft werden kann.
 * @author fabian
 *
 */
public class SyntaxChecker {

	/**
	 *  Überprüft, ob ein String in einen Integerwert umgewandelt werden kann.
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
	
	public static boolean isPortNumber(String s)
	{
		if(isNummeric(s) && Integer.parseInt(s) > 1023 && Integer.parseInt(s) < 65536)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 *  Überprüft, ob der angegebende String eine IP Adresse bildet.
	 * @param ref String, welcher zum überprüfen übergeben wird.
	 * @return gibt zurück, ob ein ref eine syntaktisch korrekte IP Adresse ist.
	 */
	public static boolean isWellFormedIpAdress(String ref) {
		
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
	 * Überprüft, ob der SessionName keine Leerzeichen oder Zeilenumbrüche besitzt.
	 * @param ref String, der überprüft werden soll
	 * @return Gibt zurück, ob ref ein gültiger SessionName ist.
	 */
	public static boolean isWellFormedSessionName(String ref) {
		return !(ref.contains(" ") || ref.contains("\n") || ref.equals(""));
	}
	
	public static boolean isWellFormedMessage(String m)
	{
		return !(m.contains("\n"));
	}
		
	
}
