package gestiondetransactionscommerciales;

import static gestiondetransactionscommerciales.MyFunctions.getFilePath;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StoreFolderPanel extends JPanel {
    private JFileChooser file_chooser;
    private File folder_chosen;
    
    public StoreFolderPanel(DataMenu data_menu) {
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
                    String target = folder_chosen.getPath();
                    String source = MyFunctions.getDirLocation();

                    File dir = new File(MyFunctions.getDirLocation());
                    if(!dir.exists()){
                        dir.mkdir();
                        Globals.FileName[] fl_names = Globals.FileName.values();
                        
                        for (Globals.FileName fl_name : fl_names) {
                            File f = new File(MyFunctions.getFilePath(fl_name));
                            f.createNewFile();
                        }
                            
                        // add new data
                        AddDataToTarget(target, Globals.map_achats,Globals.FileName.Achats);
                        AddDataToTarget(target, Globals.map_articles,Globals.FileName.Articles);
                        AddDataToTarget(target, Globals.map_categoriesArticles,Globals.FileName.CategoriesArticles);
                        AddDataToTarget(target, Globals.map_clients,Globals.FileName.Clients);
                        AddDataToTarget(target, Globals.map_detailsAchat,Globals.FileName.DetailAchat);
                        AddDataToTarget(target, Globals.map_detailsVente,Globals.FileName.DetailVente);
                        AddDataToTarget(target, Globals.map_fournisseurs,Globals.FileName.Fournisseurs);
                        AddDataToTarget(target, Globals.map_paiements,Globals.FileName.Paiements);
                        AddDataToTarget(target, Globals.map_recus,Globals.FileName.Recus);
                        AddDataToTarget(target, Globals.map_ventes,Globals.FileName.Ventes);
                        AddDataToTarget(target, Globals.map_villes,Globals.FileName.Villes);
                        
                        //set serial numbers
                        try{
                            Globals.map_serialNbs = new HashMap();
                            Globals.map_serialNbs.put(Globals.ParentClass.Transaction, Transaction.getNoSerie());
                            Globals.map_serialNbs.put(Globals.ParentClass.Article, Article.getNoSerie());
                            Globals.map_serialNbs.put(Globals.ParentClass.CategorieArticle, CategorieArticle.getNoSerie());
                            Globals.map_serialNbs.put(Globals.ParentClass.Compte, Compte.getNoSerie());
                            Globals.map_serialNbs.put(Globals.ParentClass.Ville, Ville.getNoSerie());

                            FileOutputStream out = new FileOutputStream(target+"\\"+Globals.FileName.SerialNbs+".txt");
                            ObjectOutputStream oos = new ObjectOutputStream(out);
                            oos.writeObject(Globals.map_serialNbs);
                            oos.flush();

                        }
                        catch(java.io.EOFException ex){}
                        catch(Exception e){
                            JOptionPane.showMessageDialog(this, "Failed to export :"+folder_chosen.getAbsolutePath()+".\r\nException : "+e, "Fail", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        
                    } 
                }
            
                        
                JOptionPane.showMessageDialog(null, "The data has been successfully exported.", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to export :"+folder_chosen.getAbsolutePath()+".\r\nException : "+e, "Fail", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
 
    
    private void AddDataToTarget(String target, LinkedHashMap data, Globals.FileName fileName) throws IOException{

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(target+"\\"+fileName+".txt");
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(data);
            oos.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StoreFolderPanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(StoreFolderPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
