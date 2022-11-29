package gestiondetransactionscommerciales;

import java.util.Comparator;

/**
 * A class representing all Comparators used for sorting different types of data.
 */
public class Comparators {

    
    
    
    /**
     * A comparator used to sort Transactions later on: mainly by description then date then transaction number.
     * @return returns a comparator for the class Transaction 
     */
    public static Comparator TransactionComparator(){
        return new Comparator(){
            @Override
            public int compare(Object o1, Object o2){
                Transaction trans1 = (Transaction)o1;        
                Transaction trans2 = (Transaction)o2;

                return trans1.compareTo(trans2);
            }
        };
    }
    
    
    
    
        
    /**
     * A comparator used to sort 'Comptes' later on by name.
     * @return returns a comparator for the class 'Compte'.
     */
    public static Comparator CompteComparator(){
        return new Comparator(){
            @Override
            public int compare(Object o1, Object o2){
                Compte comp1 = (Compte)o1;        
                Compte comp2 = (Compte)o2;

                return comp1.compareTo(comp2);
            }
        };
    }
    
    
    
    
    /**
     * A comparator used to sort 'Ville' later on by name.
     * @return returns a comparator for the class 'Ville'. 
     */
    public static Comparator VilleComparator(){
        return new Comparator(){
            @Override
            public int compare(Object o1, Object o2){
                Ville v1 = (Ville)o1;
                Ville v2 = (Ville)o2;

                return v1.compareTo(v2);
            }
        };
    }
    
    
    
    
    
    
    /**
     * A comparator used to sort 'Article' later on by name.
     * @return returns a comparator for the class 'Article'. 
     */
    public static Comparator ArticleComparator(){
        return new Comparator(){
            @Override
            public int compare(Object o1, Object o2){
                Article a1 = (Article)o1;        
                Article a2 = (Article)o2;

                return a1.compareTo(a2);
            }
        };
    }
    
    
    
    
    
    /**
     * A comparator used to sort 'Categories' by name.
     * @return returns a comparator for the class 'CategorieArticle'.
     */
    public static Comparator CategorieArticleComparator(){
        return new Comparator(){
            @Override
            public int compare(Object o1, Object o2){
                CategorieArticle c1 = (CategorieArticle)o1;
                CategorieArticle c2 = (CategorieArticle)o2;
                
                return c1.compareTo(c2);
            }
        };
    }
    
    
}
