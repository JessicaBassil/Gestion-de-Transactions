package gestiondetransactionscommerciales;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class LoadFolderPanel extends JPanel {
    private JFileChooser file_chooser;
    private File folder_chosen;
    
    public LoadFolderPanel(DataMenu data_menu) {
        Globals.getFileAchats();
        Globals.getFileArticles();
        Globals.getFileCategorieArticle();
        Globals.getFileClients();
        Globals.getFileDetailAchat();
        Globals.getFileDetailVente();
        Globals.getFileFournisseur();
        Globals.getFilePaiements();
        Globals.getFileRecus();
        Globals.getFileVentes();
        Globals.getFileVilles();
        Globals.getSerialNbs();
        
        file_chooser = new JFileChooser();
        file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        
        this.add(file_chooser);
    
        int result = file_chooser.showOpenDialog(this);
        
        if(result == JFileChooser.APPROVE_OPTION){
            try{
                folder_chosen = file_chooser.getSelectedFile();
                
                if(folder_chosen.isDirectory()){
                    String str_path = folder_chosen.getPath();
                        
                        //achat
                        LinkedHashMap col = new LinkedHashMap();
                        FileInputStream in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Achats+".txt");
                        ObjectInputStream ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                
                        Iterator it = col.entrySet().iterator();
                        while(it.hasNext()){
                                
                            Map.Entry<Integer, Achat> pair = (Map.Entry<Integer, Achat>)it.next();
                            Achat obj = pair.getValue();
                            obj.setNewNoTransaction();
                            Globals.map_achats.put(obj.getNoTrans(), obj);
                               
                        }
                        MyFunctions.AddSortedDataAchats();
                    
                        
                        //Article
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Articles+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                                
                            Map.Entry<Integer, Article> pair = (Map.Entry<Integer, Article>)it.next();
                            Article obj = pair.getValue();
                            obj.setNewNoArticle();
                            Globals.map_articles.put(obj.getNoArticle(), obj);
                        
                        }
                    
                       
                        //Categories Article
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.CategoriesArticles+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                                
                            Map.Entry<Integer, CategorieArticle> pair = (Map.Entry<Integer, CategorieArticle>)it.next();
                            CategorieArticle obj = pair.getValue();
                            obj.setNewNoCategorie();
                            Globals.map_categoriesArticles.put(obj.getNoCategorie(), obj);
                        
                        }
                    
                        
                        //Clients
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Clients+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                            
                            Map.Entry<Integer, Client> pair = (Map.Entry<Integer, Client>)it.next();
                            Client obj = pair.getValue();
                            obj.setNewNoCompte();
                            Globals.map_clients.put(obj.getNoCompte(), obj);
                        
                        }
                    
                        
                        //Fournisseurs
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Fournisseurs+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                            
                            Map.Entry<Integer, Fournisseur> pair = (Map.Entry<Integer, Fournisseur>)it.next();
                            Fournisseur obj = pair.getValue();
                            obj.setNewNoCompte();
                            Globals.map_fournisseurs.put(obj.getNoCompte(), obj);
                        
                        }
                        
                        
                        
                        
                        //Paiements
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Paiements+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                            
                            Map.Entry<Integer, Paiement> pair = (Map.Entry<Integer, Paiement>)it.next();
                            Paiement obj = pair.getValue();
                            obj.setNewNoTransaction();
                            Globals.map_paiements.put(obj.getNoTrans(), obj);
                        
                        }
                        
                        
                        
                        //Recus
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Recus+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                            
                            Map.Entry<Integer, Recu> pair = (Map.Entry<Integer, Recu>)it.next();
                            Recu obj = pair.getValue();
                            obj.setNewNoTransaction();
                            Globals.map_recus.put(obj.getNoTrans(), obj);
                        
                        }
                        
                        
                        
                        //Ventes
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Ventes+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                            
                            Map.Entry<Integer, Vente> pair = (Map.Entry<Integer, Vente>)it.next();
                            Vente obj = pair.getValue();
                            obj.setNewNoTransaction();
                            Globals.map_ventes.put(obj.getNoTrans(), obj);
                        
                        }
                        
                        
                        
                        
                        //Villes
                        col = new LinkedHashMap();
                        in = new FileInputStream(""+str_path+"\\"+Globals.FileName.Villes+".txt");
                        ois = new ObjectInputStream(in);
                        col = (LinkedHashMap)(ois.readObject());
                        
                        it = col.entrySet().iterator();
                        while(it.hasNext()){
                            
                            Map.Entry<Integer, Ville> pair = (Map.Entry<Integer, Ville>)it.next();
                            Ville obj = pair.getValue();
                            obj.setNewNoVille();
                            Globals.map_villes.put(obj.getnoVille(), obj);
                        
                        }
                        
                        
                        Globals.setSerialNbs();
                        MyFunctions.AddSortedDataAchats();
                        MyFunctions.AddSortedDataPaiements();
                        MyFunctions.AddSortedDataRecus();
                        
                        MyFunctions.AddSortedDataVentes();
                        MyFunctions.AddSortedDataArticles();
                        MyFunctions.AddSortedDataVilles();
                        
                        MyFunctions.AddData(Globals.FileName.Clients, Globals.map_clients);
                        MyFunctions.AddData(Globals.FileName.Fournisseurs, Globals.map_fournisseurs);
                        MyFunctions.AddData(Globals.FileName.CategoriesArticles, Globals.map_categoriesArticles);
                        
                        JOptionPane.showMessageDialog(null, "The data has been successfully imported.", "Saved", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to import :"+folder_chosen.getAbsolutePath()+".\r\n"
                            + "Make sure you choose the appropriate folder.", "Fail", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(this, "Failed to import :"+folder_chosen.getAbsolutePath()+".\r\n"
                        + "Make sure you choose the appropriate folder.\r\n"
                        + "Exception : "+e, "Fail", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
