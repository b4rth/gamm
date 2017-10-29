package fr.bart.gamm.util;

public enum Error {

	ADDRESS_DOES_NOT_EXIST("adresseExistePas", "L'adresse renseignée n'existe pas");
	
	private String label;
	private String message;
	
	private Error(String label, String message) {
		this.label = label;
		this.message = message;
	}

	public static Error getByLabel(String label) {
		if(label != null) {
			for(Error currentError : values()) {
				if(currentError.label.equals(label)) {
					return currentError;
				}
			}			
		}
		return null;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getMessage() {
		return message;
	}
}
