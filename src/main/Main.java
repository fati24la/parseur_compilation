package main;


public class Main {
	  public static void main(String[] args) {
		  //String ph = "les fromage mange les souris";
		  //String ph = "une fromage mange les souris";
		  //String ph = "les souris mangent le fromage";
		  //String ph = "une souris mange le fromage .";
		  //String ph = "fromage mange les souris";
		  //String ph = "un fromage mange des souris";
		  //String ph = "des souris mange fromage";
		  //String ph = "une souris mange le fromage , la souris mange le fromage .";
		  //String ph = "une souris mange le fromage ! une souris mange le fromage ?";
		  //String ph = "ils mangent le fromage .";
		  //String ph = "la petite souris mange le fromage ";
		  //String ph = "la petite souris mange le fromage blanc";
		  //String ph = "ils boivent l' eau .";
		  String ph = "la souris mange le fromage , il boit le lait .";
		 

	        TokenManager tm = new TokenManager(ph);
	        Parseur parser = new Parseur(tm);

	        try {
	            parser.parse();
	            System.out.println(ph + " est valide");
	        } catch (RuntimeException e) {
	            System.out.println(ph + " n'est pas valide");
	            System.out.println(e.getMessage());
	        }
	    }
}
