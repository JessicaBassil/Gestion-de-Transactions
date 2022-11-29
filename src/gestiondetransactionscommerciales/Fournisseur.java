package gestiondetransactionscommerciales;

public class Fournisseur extends Compte{

    public Fournisseur(String nomCompte, Ville ville) {
        super(nomCompte, ville);
    }

    
    public void debiter(double montant) {
        double solde = super.getSolde() + montant;
        super.setSolde(solde);
    }

    
    public void crediter(double montant) {
        if(super.getSolde()>=montant){ 
            double solde = super.getSolde() - montant;
            super.setSolde(solde);
        }
    }

    
}
