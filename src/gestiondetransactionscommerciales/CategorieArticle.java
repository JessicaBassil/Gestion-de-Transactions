package gestiondetransactionscommerciales;

import java.io.Serializable;

class CategorieArticle implements Serializable, Comparable{
    private static int noSerie =1;

    private final int noCategorie;
    private String nomCategorie;
    
    CategorieArticle(){
        this.noCategorie = noSerie;
        noSerie++;
    }
    
    public int getNoCategorie(){ return this.noCategorie;}
    public String getNomCategorie() { return this.nomCategorie;}
    
    public void setNomCategorie(String nom) { this.nomCategorie = nom;}
    
    
    
    public static int getNoSerie() { return CategorieArticle.noSerie;}
    
    
    /**
     * A function that sets the serial number :
     * Once the application is closed this number is 1 by default which is not accurate if the user already added any instance of this class before.
     * This function is used in order to set the actual serial number from the data files.
     * @param noSerie get the actual serial number. 
     */
    public static void setNoSerie(int noSerie){ CategorieArticle.noSerie = noSerie;}
    
    public String toString(){
        return this.nomCategorie;
    }

    @Override
    public int compareTo(Object o) {
        CategorieArticle c1 = (CategorieArticle)this;
        CategorieArticle c2 = (CategorieArticle)o;
        return c1.nomCategorie.compareTo(c2.nomCategorie);
    }
    
}
