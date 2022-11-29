package gestiondetransactionscommerciales;


import java.io.Serializable;
import java.time.LocalDate;

/**
 * An abstract class that represents Transactions.
 */
    
public abstract class Transaction implements Serializable, Comparable{

    private static int noSerie = 1;
    private int noTransaction;
    private Description description;
    private LocalDate dateTransaction;
    private double montant;
    private TypeP typePaiement;
       
    enum TypeP{
        CASH,
        CHEQUE,
        TRANSFERT
    }
    
    enum Description{
        Vente,
        Recu,
        Achat,
        Paiement;

        static Description valueOf(Object valueAt) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    public Transaction(LocalDate dateTransaction, double montant) {
        this.montant = montant;
        this.dateTransaction = dateTransaction;
        noTransaction = noSerie;
        noSerie++;
        typePaiement = TypeP.CASH;
    }

    
    public void setTypePaiement(Transaction.TypeP typeP){ this.typePaiement = typeP;}
    public TypeP getTypePaiement(){ return this.typePaiement;}
    
    
    
    /**
     * A function that sets the serial number :
     * Once the application is closed this number is 1 by default which is not accurate if the user already added any instance of this class before.
     * This function is used in order to set the actual serial number from the data files.
     * @param noSerie get the actual serial number. 
     */
    public static void setNoSerie(int noSerie){ Transaction.noSerie = noSerie;}
    
    public static int getNoSerie(){ return Transaction.noSerie; }
    
    
    
    
    public int getNoTrans(){ return this.noTransaction; }
    public Description getDesc(){ return this.description; }
    public LocalDate getDateTrans(){ return this.dateTransaction; }
    public double getMontant(){ return this.montant; }
    
    public void setDateTrans(LocalDate dateTrans){ this.dateTransaction = dateTrans; }
    public void setMontant(double m){ this.montant = m;}
    
    /**
     * A function to set description.
     */
    public void setDescription(Description desc){
        this.description = desc;
    };
    
    
    
    
    
    /**
     * A function to set the variable 'montant'.
     * @param montant
     */
    public abstract void miseAJourCompte(double montant);
    
    public abstract void UndoMiseAJourCompte(double montant);
    
    
    /**
     * A function used to compare two Transactions mainly by description then date then number.
     * @param o transaction 
     * @return integer that represents which transaction should be before the other 
     */
    public int compareTo(Object o){
                    
        Transaction trans1 = (Transaction)this;        
        Transaction trans2 = (Transaction)o;

        int res = 0;
        int i_res_desc = trans1.getDesc().compareTo(trans2.getDesc());
        int i_res_date = trans1.getDateTrans().compareTo(trans2.getDateTrans());
        int i_res_no = trans1.getNoTrans() - trans2.getNoTrans();
                
        if(i_res_desc!=0) { res = i_res_desc; }
        else if(i_res_date!=0){ res = i_res_date; }
        else { res = i_res_no; }
                    
        return res;
    }
    
    
    
    
    /**
     * Function to print out properties of a transaction.
     * @return number, description, date and 'montant' of a transaction
     */
    public String toString(){
        return this.noTransaction+" : "+this.dateTransaction+", "+this.montant;
    }
        

}

