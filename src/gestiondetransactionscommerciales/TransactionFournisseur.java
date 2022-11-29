package gestiondetransactionscommerciales;

import java.time.LocalDate;

public abstract class TransactionFournisseur extends Transaction{

    private Fournisseur fournisseur;
    
    public TransactionFournisseur(LocalDate dateTransaction, double montant, Fournisseur fournisseur) {
        super(dateTransaction, montant);
        this.fournisseur = fournisseur;
    }
    
    public Fournisseur getFournisseur(){ return this.fournisseur; }
    
    public String ToString(){
        return super.toString();
    }
}
