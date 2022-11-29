package gestiondetransactionscommerciales;


import java.time.LocalDate;

public class Recu extends TransactionClient{

    public Recu(LocalDate dateTransaction, double montant, Client client) {
        super(dateTransaction, montant, client);
        super.setDescription(Description.Recu);
    }

    public void miseAJourCompte(double montant) {
        super.getClient().crediter(montant);
        super.setMontant(montant);
    }

    public String toString(){
        return super.ToString();
    }
    
    
    @Override
    public void UndoMiseAJourCompte(double montant){
        super.getClient().debiter(montant);
    }
}
