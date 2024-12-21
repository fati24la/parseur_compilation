package main;


public class TokenManager {
	private String[] entree;
    private int iCourant;

    public TokenManager(String ch) {
        this.entree = ch.split(" ");
    }

    String suivant() {
    	if (iCourant < entree.length) {
    		return entree[iCourant++];
    	}
    return null;	
    }

}
