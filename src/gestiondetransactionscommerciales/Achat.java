
package gestiondetransactionscommerciales;


import java.time.LocalDate;

public class Achat extends TransactionFournisseur{

    public Achat(LocalDate dateTransaction, double montant, Fournisseur fournisseur) {
        super(dateTransaction, montant, fournisseur);
        super.setDescription(Description.Achat);
    }

    public double calculerTotalAchat(){
        return super.getMontant();
    }
    
    
    @Override
    public void miseAJourCompte(double montant) {
        super.getFournisseur().debiter(montant);
        super.setMontant(montant);
    }
    
    
    
    @Override
    public void UndoMiseAJourCompte(double montant){
        super.getFournisseur().crediter(montant);
    }
    
    
    @Override
    public String toString(){
        return super.ToString();
    }
    
    
}
