package gestiondetransactionscommerciales;

import java.time.LocalDate;

public class Vente extends TransactionClient{

    public Vente(LocalDate dateTransaction, double montant, Client client) {
        super(dateTransaction, montant, client);
        super.setDescription(Description.Vente);
    }

    public double CalculerTotalVente(){
        return super.getMontant();
    }
    
    
    @Override
    public void miseAJourCompte(double montant) {
        super.getClient().debiter(montant);
        super.setMontant(montant);
    }
    
    @Override
    public void UndoMiseAJourCompte(double montant){
        super.getClient().crediter(montant);
    }
    
    @Override
    public String toString(){
        return super.ToString();
    }
    
}
