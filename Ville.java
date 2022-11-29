package gestiondetransactionscommerciales;


import java.io.Serializable;

class Ville implements Serializable,Comparable{
    private static int noSerie = 1;
    private int noVille;
    private String nomVille;
    
    
    
    Ville(String nom){
        this.nomVille = nom;
        noVille = noSerie;
        noSerie ++;
    }
    
    
    
    public void setNewNoVille(){ this.noVille = noSerie; noSerie++; }
    
    
    
    /**
     * A function that sets the serial number :
     * Once the application is closed this number is 1 by default which is not accurate if the user already added any instance of this class before.
     * This function is used in order to set the actual serial number from the data files.
     * @param noSerie get the actual serial number. 
     */
    public static void setNoSerie(int noSerie){ Ville.noSerie = noSerie;}
    
    
    public static int getNoSerie(){ return Ville.noSerie; }
    
    
    
    public int getnoVille(){ return this.noVille;}
    public String getNomVille(){ return this.nomVille;}
    
    public void setNomVille(String nomVille){ this.nomVille = nomVille;}
    
    
    
    
    public String toString(){
        return this.nomVille;
    }
    
    
    
    public int compareTo(Object o) {
        Ville v1 = (Ville)this;
        Ville v2 = (Ville)o;
        return v1.getNomVille().compareTo(v2.getNomVille());
    }
    
    
}

