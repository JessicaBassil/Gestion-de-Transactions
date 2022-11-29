package gestiondetransactionscommerciales;

import java.time.LocalDate;
/**
 * Classe abstraite, sous-classe de Transaction, superclasse de Vente et Recu.
 */
public abstract class TransactionClient extends Transaction{

    private Client client;
    
    public TransactionClient(LocalDate dateTransaction, double montant, Client client) {
        super(dateTransaction, montant);
        this.client = client;
    }
    
    public Client getClient(){ return this.client; }
    
    public String ToString(){
        return super.toString();
    }
    
}
