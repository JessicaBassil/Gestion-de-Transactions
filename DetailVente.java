package gestiondetransactionscommerciales;


import java.io.Serializable;

public class DetailVente implements Serializable{

    
    private Vente vente;
    private Article article;
    private int quantite;
    
    private Double prixvente;
    
    public DetailVente(Vente vente, Article article, int quantite) {
        this.vente = vente;
        this.article = article;
        this.quantite = quantite;
        this.article.entreeStock(quantite, article.getCoutAchatParUnite());
        prixvente = article.getPrixVenteParUnite();
    }
    
    public Vente getVente(){ return this.vente;}
    public Article getArticle(){ return this.article;}
    public int getQuantite(){ return this.quantite;}
    public double getPrixVente(){return this.prixvente;}
    
    public void setArticle(Article article){ 
        this.article = article;
        this.prixvente = article.getPrixVenteParUnite();
    }
    public void setQuantite(int quantite){ 
        this.article.sortieStock(this.quantite);
        this.article.entreeStock(quantite, this.article.getCoutAchatParUnite());
        this.quantite = quantite;
    }
    public void setVente(Vente v){ this.vente = v;}
    
    public double calculerMontant(){
        return (quantite*article.getPrixVenteParUnite());
    }
    
    
    
    
}