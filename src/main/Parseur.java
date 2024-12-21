package main;


public class Parseur {
	private TokenManager tm;
	private String tc; 
	private boolean pluriel;
	private boolean masculin;
	private boolean illogique;


	private void avancer() {
		tc = tm.suivant();
	}

	private void consommer(String attendu) {
		if (tc.equals(attendu)) {
			avancer();
		} else
			throw new RuntimeException("Erreur: attendu '" + attendu + "' mais trouvé '" + tc + "'");
	}

	public Parseur(TokenManager tm) {
		this.tm=tm;
		avancer();
	}

	// Axiome==> Sujet Verbe Complement
	private void Axiome() {
		Sujet();
		Verbe();
		Complement();
		illogique=false;
		// Gérer la ponctuation ici
        if (tc != null && (tc.equals(",") || tc.equals(".") || tc.equals("!") || tc.equals("?"))) {
            Ponctuation();
            if (tc != null) {
                Axiome();  // Appeler récursivement Axiome pour traiter une autre phrase après la virgule
            }
        }
	}
	
	
	//Sujet==> Article Nom || Nom
	private void Sujet() {
		if ( tc.equals("la") || tc.equals("les") || tc.equals("une") || tc.equals("des") ) {
			Article();
			if (isAdjectif()) {
                Adjectif();
            }
			Nom();
		}else if(tc.equals("souris")){
			Nom();
		}else if(tc.equals("fromage")){
			illogique=true;
			Nom();
		}else if(tc.equals("je") || tc.equals("tu") || tc.equals("il") || tc.equals("elle") || tc.equals("nous") || tc.equals("vous") || tc.equals("ils") || tc.equals("elles")){
			Pronom();
		}else throw new RuntimeException("Erreur:" +tc);
	}
		
 
	//Verbe==> "mange" || "mangent"
	private void Verbe() {
		if ( !illogique && pluriel && tc.equals("mangent")) {
			consommer("mangent");
		}else if (!illogique && !pluriel && tc.equals("mange") ) {
			consommer("mange");
		} else if (!illogique && !pluriel && tc.equals("boit")) {
            consommer("boit");
        } else if (!illogique && pluriel && tc.equals("boivent")) {
            consommer("boivent");
        }else throw new RuntimeException("Erreur:" +tc);
	}
	
	//Complement==> Article Nom || Nom
	private void Complement() {
		if ( tc.equals("le") || tc.equals("la") || tc.equals("l'") || tc.equals("les") || tc.equals("un")|| tc.equals("une") || tc.equals("des") ) {
			Article();
			if (isAdjectif()) {
                Adjectif();
            }
			Nom();
		}else if(tc.equals("souris") || tc.equals("fromage")){
	         Nom();
		}else if(tc.equals("lait") || tc.equals("eau")){
	         Nom();
		}else throw new RuntimeException("Erreur: " +tc);
	}
	
	//Article==> "la" || "le" ||....
	private void Article() {
		if ( tc.equals("le")) {
			pluriel=false;
			masculin=true;
			consommer("le");
		}else if ( tc.equals("la")){
			pluriel=false;
			masculin=false;
			consommer("la");
		}else if ( tc.equals("l'")){
			pluriel=false;
			consommer("l'");
		}else if ( tc.equals("un")){
			pluriel=false;
			masculin=true;
			consommer("un");
		}else if ( tc.equals("une")){
			pluriel=false;
			masculin=false;
			consommer("une");
		}else if ( tc.equals("les")){
			pluriel=true;
			consommer("les");
		}else if ( tc.equals("des")){
			pluriel=true;
			consommer("des");
		}else throw new RuntimeException("Erreur: " +tc);
	}
	
	//Nom==> "souris" || "fromage" ||...
	private void Nom() {
		if ( pluriel && tc.equals("souris")) {
			consommer("souris");
		}else if ( !masculin && tc.equals("souris")) {
			consommer("souris");
		}else if (masculin && tc.equals("fromage")) {
			consommer("fromage" );
		}else if (masculin && (tc.equals("lait") || tc.equals("eau"))) {
			consommer(tc);
		} else if (tc.matches("^[aeiouh].*")) {  // Pour les noms après "l'" (commençant par voyelle ou 'h')
	        consommer(tc);
	    }else throw new RuntimeException("Erreur: " +tc);
		
		 // Validation d'un adjectif postposé (optionnel)
	    if (isAdjectif()) {
	        Adjectif();
	    }
	}
	
	// Ponctuation ==> "," || "." || "!" || "?"
    private void Ponctuation() {
        if (tc.equals(",") || tc.equals(".") || tc.equals("!") || tc.equals("?")) {
            avancer();  // Consommer la ponctuation
        } else {
            throw new RuntimeException("Erreur: ponctuation incorrecte '" + tc + "'");
        }
    }
    
    //Pronom==> "je" || "tu" ||....
  	private void Pronom() {
  		if (tc.equals("je") || tc.equals("tu") || tc.equals("il") || tc.equals("elle")) {
            pluriel = false;
        } else if (tc.equals("nous") || tc.equals("vous") || tc.equals("ils") || tc.equals("elles")) {
            pluriel = true;
        }
        avancer();  // Consommer le pronom
    }
  	
  	// Adjectif ==> "grand" || "petit" || "blanc" || "beau" || "nouveau" || "vieux" || ...
    private void Adjectif() {
        if (tc.equals("grand") || tc.equals("petit") || tc.equals("beau") || tc.equals("delicieux") || tc.equals("blanc")) {
            masculin =true;
        	consommer(tc);
        } else if (tc.equals("grande") || tc.equals("petite") || tc.equals("belle") || tc.equals("delicieuse") || tc.equals("blanche")) {
        	masculin =false;
        	consommer(tc);
        }else if (tc.equals("grandes") || tc.equals("petites") || tc.equals("belles") || tc.equals("delicieuses") || tc.equals("blanches")){
        	masculin =false;
        	pluriel =true;
        	consommer(tc);
        }else if (tc.equals("grands") || tc.equals("petits") || tc.equals("beaux") || tc.equals("delicieux") || tc.equals("blancs")){
        	masculin =true;
        	pluriel =true;
        	consommer(tc);
        }
        else throw new RuntimeException("Erreur : adjectif attendu, mais trouvé '" + tc + "'");
   
    }
    
    private boolean isAdjectif() {
        return tc.equals("grand") || tc.equals("petit") || tc.equals("beau") || 
               tc.equals("delicieux") || tc.equals("blanc") || tc.equals("grande") ||
               tc.equals("petite") || tc.equals("belle") || tc.equals("delicieuse") || 
               tc.equals("blanche") || tc.equals("grandes") || tc.equals("petites") || 
               tc.equals("belles") || tc.equals("delicieuses") || tc.equals("blanches") ||
               tc.equals("grands") || tc.equals("petits") || tc.equals("beaux") || 
               tc.equals("delicieux") || tc.equals("blancs");       
    }
    
   
	public void parse() {
		Axiome();
		if (tc != null)
			throw new RuntimeException("Erreur: "+tc);
	}
}
