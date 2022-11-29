package gestiondetransactionscommerciales;


import java.time.LocalDate;

public class Paiement extends TransactionFournisseur{

    public Paiement(LocalDate dateTransaction, double montant, Fournisseur fournisseur) {
        super(dateTransaction, montant, fournisseur);
        super.setDescription(Description.Paiement);
    }

   
    
    public void miseAJourCompte(double montant) {
        super.getFournisseur().crediter(montant);
    }

    public String toString(){
        return super.ToString();
    }

    
    @Override
    public void UndoMiseAJourCompte(double montant) {
        super.getFournisseur().debiter(montant);
    }
    

}
