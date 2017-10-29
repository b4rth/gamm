package fr.bart.gamm.util;

public enum Action {
	
	CREATE("create"),
	SEARCH_NEAR_STORES("chercherMagasinsProches"),
	DELETE("delete");
	
	private String label;
	
	private Action(String label) {
		this.label = label;
	}

	public static Action getByLabel(String label) {
		if(label != null) {
			for(Action currentAction : values()) {
				if(currentAction.label.equals(label)) {
					return currentAction;
				}
			}			
		}
		return null;
	}
	
	public String getLabel() {
		return label;
	}
	
}
