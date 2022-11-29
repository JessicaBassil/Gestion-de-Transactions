package gestiondetransactionscommerciales;

import java.io.Serializable;

public class DetailAchat implements Serializable{

    private Achat achat;
    private Article article;
    private int quantite;
    private double Cout;
    
    public DetailAchat(Achat achat, Article article, int quantite, double Cout) {
        this.Cout = Cout;
        this.achat = achat;
        this.article = article;
        this.quantite = quantite;
        this.article.sortieStock(quantite);
    }
    
    
    public Achat getAchat(){ return this.achat;}
    public Article getArticle() { return this.article;}
    public int getQuantite(){ return this.quantite; }
    public double getCout(){ return this.Cout;}
    
    public void setAchat(Achat achat){ this.achat = achat;}
    public void setArticle(Article article){ 
        this.article = article;
        this.Cout = article.getCoutAchatParUnite();
    }
    public void setQuantite(int quantite) { 
        this.article.entreeStock(this.quantite, Cout);
        this.article.sortieStock(quantite);
        this.quantite = quantite;
    }
    
    
    
    public double calculerMontant(){
        return (quantite*Cout);
    }
}