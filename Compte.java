package gestiondetransactionscommerciales;

import java.io.Serializable;


public abstract class Compte implements Serializable, Comparable{

    private static int noSerie = 1;
    private int noCompte;
    private String nomCompte;
    private Ville ville;
    private double solde;
    private EtatCompte etat;
    
    enum EtatCompte{
        ACTIF,
        FERME,
        SUSPENDU
    }

    
    public Compte(String nomCompte, Ville ville) {
        this.nomCompte = nomCompte;
        this.ville = ville;
        this.noCompte = noSerie;
        noSerie++;
        this.etat= EtatCompte.ACTIF;
    }
    
    
    
    public void setNewNoCompte(){ this.noCompte = noSerie; noSerie++; }
    
    
    
    /**
     * A function that sets the serial number :
     * Once the application is closed this number is 1 by default which is not accurate if the user already added any instance of this class before.
     * This function is used in order to set the actual serial number from the data files.
     * @param noSerie get the actual serial number. 
     */
    public static void setNoSerie(int noSerie){ Compte.noSerie = noSerie;}
    
    public static int getNoSerie(){ return Compte.noSerie; }
    
    
    public int getNoCompte(){ return this.noCompte;}
    public String getNomCompte(){ return this.nomCompte;}
    public Ville getVilleCompte(){ return this.ville;}
    public double getSolde(){ return this.solde; }
    public EtatCompte getEtatCompte(){ return this.etat; }
    
    public void setNomCompte(String nomCompte){ this.nomCompte = nomCompte;}
    public void setVille(Ville ville){ this.ville = ville;}
    public void setEtat(EtatCompte etat){ this.etat = etat;}
    public void setSolde(double solde){ this.solde = solde;}
    
    
    
    
    /**
     * A function used to compare two Transactions mainly by description then date then number.
     * @param o transaction 
     * @return integer that represents which transaction should be before the other 
     */
    public int compareTo(Object o){
        Compte c = (Compte)o;
        return this.nomCompte.compareTo(c.nomCompte);
    }
    
    

    
    public abstract void debiter(double montant);
    
    public abstract void crediter(double montant);
    
    
    
    public String toString(){
        return this.nomCompte+" : "+this.noCompte;
    }
    
}
