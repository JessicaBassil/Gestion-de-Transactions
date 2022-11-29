package gestiondetransactionscommerciales;


import java.io.Serializable;

public class Article implements Serializable, Comparable{

    private static int noSerie =1;
    private int noArticle;
    private String nomArticle;
    private int qteStock;
    private double prixVenteParUnite;
    private double coutAchatParUnite;
    private double tauxProfit;
    private CategorieArticle categorie;
    
    
    public Article(String nomArticle, double tauxProfit) {
        this.noArticle = noSerie;
        this.nomArticle = nomArticle;
        this.tauxProfit = tauxProfit;
        this.prixVenteParUnite = 0.0;
        this.coutAchatParUnite = 0.0;
        this.qteStock = 0;
        noSerie++;
        categorie = new CategorieArticle();
    }
    
    
    
    /**
     * A function that sets the serial number :
     * Once the application is closed this number is 1 by default which is not accurate if the user already added any instance of this class before.
     * This function is used in order to set the actual serial number from the data files.
     * @param noSerie get the actual serial number. 
     */
    public static void setNoSerie(int noSerie){ Article.noSerie = noSerie;}
    
    public static int getNoSerie(){ return Article.noSerie; }
            
    
    public void setNewNoArticle(){ this.noArticle = noSerie; noSerie++; }
            
    
    public int getNoArticle(){return this.noArticle;}
    public String getNomArticle(){ return this.nomArticle;}
    public int getQteStock(){ return this.qteStock; }
    public double getPrixVenteParUnite(){ return this.prixVenteParUnite;}
    public double getCoutAchatParUnite(){ return this.coutAchatParUnite;}
    public double getTauxProfit(){ return this.tauxProfit; }
    public CategorieArticle getCategorieArticle(){ return this.categorie; }
    
    public void setCategorieArticle(CategorieArticle catart){ categorie = catart; }
    public void setPrixVenteParUnite(){ this.prixVenteParUnite = (1 + tauxProfit)*coutAchatParUnite;}
    public void setProfit(double tauxProfit){ this.tauxProfit= tauxProfit;}
    public void setNom(String nom){ this.nomArticle = nom;}
    
    public void setCoutAchatParUnite(double cout){ 
        this.coutAchatParUnite = cout;
        this.setPrixVenteParUnite();
    }
    
    
    
    @Override
    public String toString(){
        return this.noArticle+": "+this.nomArticle+", qte: "+this.qteStock;
    }

    
    
    @Override
    public int compareTo(Object o) {
        Article a1 = (Article)this;
        Article a2 = (Article)o;
        return a1.nomArticle.compareTo(a2.nomArticle);
    }
    
    
    
    void entreeStock(int qteStock, double coutAchatParUnite){
        this.qteStock += qteStock;
        this.coutAchatParUnite = coutAchatParUnite;
        this.setPrixVenteParUnite();
    }
    
    
    
    boolean sortieStock(int qte){
        if(qteStock<qte){return false;}
        this.qteStock-=qte;
        return true;
    }
}

