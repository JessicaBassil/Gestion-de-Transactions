package gestiondetransactionscommerciales;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JComboBox;

/**
 * A class containing global variables and objects to be set and get throughout the applications.
*/
public class Globals {

        
    enum FileName{
        Ventes,
        Achats,
        Recus,
        Paiements,
        Clients,
        Fournisseurs,
        Articles,
        CategoriesArticles,
        SerialNbs,
        DetailVente,
        DetailAchat
    }
    
    
    enum ParentClass{
        Transaction,
        Article,
        CategorieArticle,
        Compte,
        DetailVente
    }
    
    
    
    public static LinkedHashMap<Integer,Vente> map_ventes = new LinkedHashMap();
    public static LinkedHashMap<Integer,Achat> map_achats = new LinkedHashMap();
    public static LinkedHashMap<Integer,Recu> map_recus = new LinkedHashMap();
    public static LinkedHashMap<Integer,Paiement> map_paiements = new LinkedHashMap();
    public static LinkedHashMap<Integer,Client> map_clients = new LinkedHashMap();
    public static LinkedHashMap<Integer,Fournisseur> map_fournisseurs = new LinkedHashMap();
    public static LinkedHashMap<Integer,Article> map_articles = new LinkedHashMap();
    public static LinkedHashMap<Integer,CategorieArticle> map_categoriesArticles = new LinkedHashMap();
    public static LinkedHashMap<String,DetailVente> map_detailsVente = new LinkedHashMap();
    public static LinkedHashMap<String,DetailAchat> map_detailsAchat = new LinkedHashMap();
    public static HashMap<ParentClass,Integer> map_serialNbs = new HashMap();
    
    
    public static void getFileVentes(){
        Globals.map_ventes = MyFunctions.getDataObjects(FileName.Ventes);
    }
    
    public static void getFileAchats(){
        Globals.map_achats = MyFunctions.getDataObjects(FileName.Achats);
    }
    
    public static void getFileRecus(){
        Globals.map_recus = MyFunctions.getDataObjects(FileName.Recus);
    }
    
    public static void getFilePaiements(){
        Globals.map_paiements = MyFunctions.getDataObjects(FileName.Paiements);
    }
    
    public static void getFileClients(){
        Globals.map_clients = MyFunctions.getDataObjects(FileName.Clients);
    }
    
    public static void getFileFournisseur(){
        Globals.map_fournisseurs = MyFunctions.getDataObjects(FileName.Fournisseurs);
    }
    
    public static void getFileArticles(){
        Globals.map_articles = MyFunctions.getDataObjects(FileName.Articles);
    }
    
    public static void getFileCategorieArticle(){
        Globals.map_categoriesArticles = MyFunctions.getDataObjects(FileName.CategoriesArticles);
    }
    
    public static void getFileDetailVente(){
        Globals.map_detailsVente = MyFunctions.getDataObjects(FileName.DetailVente);
    }
    
    public static void getFileDetailAchat(){
        Globals.map_detailsAchat  = MyFunctions.getDataObjects(FileName.DetailAchat);
    }
    
    
    
    
    private static String[] ville_names = {"Berlin","Cologne","Dresden","Dusseldorf","Frankfurt","Hamburg","Hanover","Leipzig","Munich","Nuremberg","Stuttgart"};

    public static LinkedHashMap<Integer,Ville> map_villes = new LinkedHashMap();
    private static JComboBox combo;
    
    
    
    
    public static void setVilles(){
        map_villes = new LinkedHashMap();
        for (String ville_name : ville_names) {
            Ville v = new Ville(ville_name);
            map_villes.put(v.getnoVille(),v);
        }
    }
    
    
    
    
    public static JComboBox getVillesCombo(){
        
        Globals.combo = new JComboBox();
        combo.addItem("--Select--");
        Globals.combo = MyFunctions.AddDataToComboBox(map_villes);
        
        return combo;
    }
    
    
    
    
    
    
    public static HashMap getSerialNbs(){
        try{
            //get old data
            FileInputStream in = new FileInputStream(MyFunctions.getFilePath(Globals.FileName.SerialNbs));
            ObjectInputStream ois = new ObjectInputStream(in);
            Globals.map_serialNbs = (HashMap)(ois.readObject());
        
            
            if(Globals.map_serialNbs!=null){
                //set serial numbers
                Transaction.setNoSerie((int)Globals.map_serialNbs.get(Globals.ParentClass.Transaction));
                Compte.setNoSerie((int)Globals.map_serialNbs.get(Globals.ParentClass.Compte));
                Article.setNoSerie((int)Globals.map_serialNbs.get(Globals.ParentClass.Article));
                CategorieArticle.setNoSerie((int)Globals.map_serialNbs.get(Globals.ParentClass.CategorieArticle));
            }
            
        
        }
        catch(java.io.EOFException ex){}
        catch(Exception e){
            System.out.println("Problem serializing out: "+e);
        }
        return Globals.map_serialNbs;
    }
    
 

    
    
    
    
    /**
     * A function that gets the current serial numbers from each concerned class and sets them in the 'SerialNbs' file as a HashMap.
     */
    public static void setSerialNbs(){
        try{
            Globals.map_serialNbs = new HashMap();
            Globals.map_serialNbs.put(Globals.ParentClass.Transaction, Transaction.getNoSerie());
            Globals.map_serialNbs.put(Globals.ParentClass.Article, Article.getNoSerie());
            Globals.map_serialNbs.put(Globals.ParentClass.CategorieArticle, CategorieArticle.getNoSerie());
            Globals.map_serialNbs.put(Globals.ParentClass.Compte, Compte.getNoSerie());
            
            FileOutputStream out = new FileOutputStream(MyFunctions.getFilePath(Globals.FileName.SerialNbs));
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(Globals.map_serialNbs);
            oos.flush();
            
        }
        catch(java.io.EOFException ex){}
        catch(Exception e){
            System.out.println("Problem serializing out: "+e);
        }
    }
    
    
}
