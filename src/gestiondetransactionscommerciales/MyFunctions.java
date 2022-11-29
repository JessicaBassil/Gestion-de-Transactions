package gestiondetransactionscommerciales;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A class that contains global functions to be used throughout the whole application.
 */
public class MyFunctions{
    
    
    
    
    public static void AddSortedDataArticles(){
        ArrayList<Article> arr_sort = new ArrayList();
        Iterator it = Globals.map_articles.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Article> pair = (Map.Entry<Integer, Article>)it.next();
            arr_sort.add(pair.getValue());
        }
        
        Collections.sort(arr_sort, Comparators.ArticleComparator());
        Globals.map_articles = new LinkedHashMap();
        
        it = arr_sort.iterator();
        while(it.hasNext()){
            Article a = (Article)it.next();
            Globals.map_articles.put(a.getNoArticle(), a);
        }
        
        MyFunctions.AddData(Globals.FileName.Articles, Globals.map_articles);
    }
    
    
    
    
    public static void AddSortedDataPaiements(){
        ArrayList<Paiement> arr_sort = new ArrayList();
        Iterator it = Globals.map_paiements.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,Paiement> pair = (Map.Entry<Integer,Paiement>)it.next();
            arr_sort.add(pair.getValue());
        }
                    
        Collections.sort(arr_sort, Comparators.TransactionComparator());
        Globals.map_paiements = new LinkedHashMap();
                    
        it = arr_sort.iterator();
        while(it.hasNext()){
            Paiement r = (Paiement)it.next();
            Globals.map_paiements.put(r.getNoTrans(),r);
        }
                    
        MyFunctions.AddData(Globals.FileName.Paiements, Globals.map_paiements);
    }
    
    
    
    
    
    
    
    public static void AddSortedDataVentes(){
        //sort again
        ArrayList<Vente> arr_sort = new ArrayList();
                    
        Iterator it = Globals.map_ventes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,Vente> pair = (Map.Entry<Integer,Vente>)it.next();
            arr_sort.add(pair.getValue());
        }
                    
        Collections.sort(arr_sort, Comparators.TransactionComparator());
        Globals.map_ventes = new LinkedHashMap();
        
        it = arr_sort.iterator();
        while(it.hasNext()){
            Vente v = (Vente)it.next();
            Globals.map_ventes.put(v.getNoTrans(),v);
        }
        MyFunctions.AddData(Globals.FileName.Ventes, Globals.map_ventes);
                    
    }
    
    
    
    
    public static void AddSortedDataAchats(){
        //sort again
        ArrayList<Achat> arr_sort = new ArrayList();
                    
        Iterator it = Globals.map_achats.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry<Integer,Achat> pair = (Map.Entry<Integer,Achat>)it.next();
            arr_sort.add(pair.getValue());
        }
        
        Collections.sort(arr_sort, Comparators.TransactionComparator());
        Globals.map_achats = new LinkedHashMap();
        it = arr_sort.iterator();
        while(it.hasNext()){
            Achat v = (Achat)it.next();
            Globals.map_achats.put(v.getNoTrans(),v);
        }         
        MyFunctions.AddData(Globals.FileName.Achats, Globals.map_achats);
                    
    }
    
    
    
    
    
    
    public static void AddSortedDataRecus(){
        //sort again
        ArrayList<Recu> arr_sort = new ArrayList();
                    
        Iterator it = Globals.map_recus.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry<Integer,Recu> pair = (Map.Entry<Integer,Recu>)it.next();
            arr_sort.add(pair.getValue());
        }
        
        Collections.sort(arr_sort, Comparators.TransactionComparator());
        Globals.map_recus = new LinkedHashMap();
        it = arr_sort.iterator();
        while(it.hasNext()){
            Recu v = (Recu)it.next();
            Globals.map_recus.put(v.getNoTrans(),v);
        }         
        MyFunctions.AddData(Globals.FileName.Recus, Globals.map_recus);
                    
    }
    
    
    
    
    /**
     * A function that get the path of the folder containing all files.
     * @return directory location
     */
    public static String getDirLocation(){ return "D:\\Java_Project";}    
    
    
    
    /**
     * A function that saves given data into a file
     * @param fileName name of the file 
     * @param in_col data in an Collection
     */
    public static void AddData(Globals.FileName fileName, LinkedHashMap in_col){
        LinkedHashMap data_col = new LinkedHashMap();
        try{
            //get old data
            data_col.putAll((LinkedHashMap)getDataObjects(fileName));
            
            // add new data
            data_col.putAll(in_col);
            FileOutputStream out = new FileOutputStream(getFilePath(fileName));
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(data_col);
            oos.flush();
            
            //set serial numbers
            Globals.setSerialNbs();
        }
        catch(java.io.EOFException ex){}
        catch(Exception e){
            System.out.println("Problem serializing in data: "+e);
        }
        
    
    }
    
    
    
    
    
    /**
     * A function that gets data from a file and returns it into an Array List.
     * @param fileName Name of file to get data from
     * @return Array List of all data
     */
    public static LinkedHashMap getDataObjects(Globals.FileName fileName){
        LinkedHashMap col = new LinkedHashMap();
        try{
            //get old data
            FileInputStream in = new FileInputStream(getFilePath(fileName));
            ObjectInputStream ois = new ObjectInputStream(in);
            col = (LinkedHashMap)(ois.readObject());
        }
        catch(java.io.EOFException ex){}
        catch(Exception e){
            System.out.println("Problem serializing out: "+e);
        }
            
        return col;
    }
    
    
    
    
    
    
    /**
     * A function that gets the full path of a file in this project.
     * @param fileName name of file
     * @return String full path.
     */
    public static String getFilePath(Globals.FileName fileName){
        return getDirLocation()+"\\"+fileName+".txt";
    }
    
    
    
    
    
    /**
     * A function that adds data given in an array list to combo Box.
     * @param combo Combo Box to add to
     * @param arr_data ArrayList containing data : must have been gotten from Global function "getData".
     */
    public static JComboBox AddDataToComboBox(LinkedHashMap map_data){
        JComboBox combo = new JComboBox();

        if(!map_data.isEmpty()){
            combo.addItem("--Select--");
            Iterator it = map_data.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                combo.addItem(pair.getValue());
            }
        }
        
        return combo;
    }
    

    
    
    
    /**
     * This function is used to make the JComboBoxes in the reports have the same width.
     * @param str_lbl initial label of the combo box.
     * @return JLabel with enough spaces to enlarge the JComboBox to the desired width.
     */
    public static JLabel ChangeWidth(String str_lbl){
        String str_fixed = "";
        int nb_chars = str_lbl.length();
        //38
        double remain = (38-nb_chars)/2;
        int nb_space = 0;
        String space = "";
        if((38-nb_chars)%2!=0){
            str_fixed = " ";
            nb_space = (38-nb_chars-1)/2;
        } else {
            nb_space = (38-nb_chars)/2;
        }
        for(int i=0; i<nb_space; i++){
            space+= " ";
        }
        str_fixed+= space + str_lbl + space;
        
        JLabel lbl = new JLabel(str_fixed, SwingConstants.CENTER);
        return lbl;
    }
    
    
    
    
    
}
