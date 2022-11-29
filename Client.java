package gestiondetransactionscommerciales;

public class Client extends Compte{

    public Client(String nomCompte, Ville ville) {
        super(nomCompte, ville);
    }

    
    public void debiter(double montant) {
        if(super.getSolde() >= montant) {
            double solde = super.getSolde() - montant;
            super.setSolde(solde);
        };
    }

    
    public void crediter(double montant) {
        double solde = super.getSolde() + montant;
        super.setSolde(solde);
    }

    
}
