package gestiondetransactionscommerciales;


import java.io.*;
import java.util.*;
import javax.swing.*;

public class MainFrame implements Observer{
        JFrame frame;
        DataMenu data_menu;
        JPanel panel;

    public MainFrame(DataMenu data_menu) {
        this.data_menu = data_menu;
        
        frame = new JFrame("Gestion de Transactions Commerciales");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        createFiles();
        
        
        frame.setJMenuBar(new MyMenuBar(data_menu, frame));
        
        
        frame.setSize(700,650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
    
    
    
    /**
     * A function that creates a main folder and its files in case they have not been created in Local Disk D
     */
    private void createFiles(){
        Globals.setVilles();
        File dir = new File(MyFunctions.getDirLocation());
        if(!dir.exists()){
            dir.mkdir();
            Globals.FileName[] fl_names = Globals.FileName.values();
            try{
                for (Globals.FileName fl_name : fl_names) {
                    File f = new File(MyFunctions.getFilePath(fl_name));
                    f.createNewFile();
                }
            }
            catch(IOException e){}
        }
        else{
            Globals.getSerialNbs();
        }
    }          
            
            
    
    
    
    

    public void update(Observable o, Object arg) {
        if(panel != null) frame.getContentPane().remove(panel);
        this.panel = data_menu.getPanel();

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }
}
