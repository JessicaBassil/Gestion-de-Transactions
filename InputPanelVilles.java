package gestiondetransactionscommerciales;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.CENTER;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class InputPanelVilles extends JPanel{
    private JPanel pan_north, pan_south, pan_left, pan_right, pan_in_data, pan_list, pan_in_cont;
    private JButton btn_creer, btn_enregistrer, btn_quitter;
    private String str_title;
    private String [] a_tab_headers;
    private DataMenu data_menu;
    private TitledBorder border;
    private JLabel lbl_no, lbl_nom, lbl_table;
    private JTextField txt_no, txt_nom, txt_tab_nom;
    private JLabel lbl_error;
    
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox combo_type;
    
    private TableColumn col_1, col_2;
    
    private Fournisseur fournisseur;
    private Client client;
    private Ville ville;
    
    private JList<Ville> list_villes;
    private DefaultListModel mod_villes;
    
    private int error_row_nb = -1;
    
    
    
    
    
    public InputPanelVilles(DataMenu data_menu) {
        Globals.getFileVilles();
        Globals.getFileClients();
        Globals.getFileFournisseur();
        //assignments
        pan_north = new JPanel();
        pan_south = new JPanel();
        pan_left = new JPanel();
        pan_right = new JPanel();
        pan_in_data = new JPanel();
        pan_list = new JPanel();
        pan_in_cont = new JPanel();
        
        btn_creer = new JButton("Creer");
        btn_enregistrer = new JButton("Enregistrer");
        btn_quitter = new JButton("Quitter");
        
        lbl_no = new JLabel("No Ville", SwingConstants.RIGHT);
        lbl_nom = new JLabel("Nom Ville", SwingConstants.RIGHT);
        lbl_table = new JLabel("Comptes");
        
        txt_no = new JTextField(19);
        txt_nom = new JTextField(19);
        txt_tab_nom = new JTextField();
        
        Border txt_border = txt_no.getBorder();
        Border actual_txt_border = BorderFactory.createCompoundBorder(txt_border, BorderFactory.createEmptyBorder(2, 0, 2, 0));
        txt_no.setBorder(actual_txt_border);
        txt_nom.setBorder(actual_txt_border);
        
        lbl_error = new JLabel();
        
        combo_type = new JComboBox();
        list_villes = new JList();
        mod_villes = new DefaultListModel();
        
        
        //Data assignments
        this.data_menu = data_menu;
        str_title = "Villes";
        border = BorderFactory.createTitledBorder(null, "Infos "+str_title, CENTER, 0);
        
        a_tab_headers = new String[]{"No Compte","Type Compte","Nom Compte","Solde"};
        tableModel = new DefaultTableModel(a_tab_headers,20){
                @Override
                public boolean isCellEditable(int row, int col) {
                     switch (col) {
                         case 1:
                         case 2:
                             return true;
                         default:
                             return false;
                      }
                }
            };
        
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
        
        
        
        //add values to combo box
        combo_type.addItem("Client");
        combo_type.addItem("Fournisseur");
        
        //get villes list
        if(!Globals.map_villes.isEmpty()){
            int i=0;
            Iterator it = Globals.map_villes.entrySet().iterator();
            
            while(it.hasNext()){
                Map.Entry<Integer,Ville> pair = (Map.Entry<Integer,Ville>)it.next();
                mod_villes.add(i,pair.getValue());
                list_villes.setModel(mod_villes);
                i++;
            }

        }
        
        

        //GUI design
        this.setBorder(border);
        
        this.setLayout(new GridLayout(2,1,10,10));
        this.add(pan_north);
        this.add(pan_south);
        
        pan_north.setLayout(new GridLayout(1,2,10,10));
        pan_north.add(pan_left);
        pan_north.add(pan_right);
        
        
        pan_south.setLayout(new BorderLayout());
        pan_south.add(lbl_table, BorderLayout.NORTH);
        pan_south.add(scroll, BorderLayout.CENTER);
        
        pan_left.setLayout(new BorderLayout(0,15));
        pan_left.add(pan_in_cont, BorderLayout.CENTER);
        pan_left.add(lbl_error, BorderLayout.SOUTH);
        
        pan_in_cont.add(pan_in_data);
        
        pan_right.setLayout(new BorderLayout(0,15));
        pan_right.add(pan_list, BorderLayout.CENTER);
        
        btn_creer.setPreferredSize(new Dimension(100,25));
        btn_enregistrer.setPreferredSize(new Dimension(100,25));
        btn_quitter.setPreferredSize(new Dimension(100,25));
       
        
        pan_in_data.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth =1;
        pan_in_data.add(btn_creer,gbc);
        
        gbc.gridx = 1;
        pan_in_data.add(btn_enregistrer,gbc);
        
        gbc.gridx = 2;
        pan_in_data.add(btn_quitter,gbc);
        
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        
        pan_in_data.add(lbl_no,gbc);
        
        gbc.gridy = 2;
        pan_in_data.add(lbl_nom,gbc);
        
        
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        
        gbc.gridy = 1;
        pan_in_data.add(txt_no,gbc);
        
        gbc.gridy = 2;
        pan_in_data.add(txt_nom,gbc);
        
        border = BorderFactory.createTitledBorder(null, str_title, CENTER, 0);
        pan_list.setBorder(border);
        pan_list.setBackground(Color.white);
        pan_list.add(list_villes);
        
        Color col = Color.getHSBColor(0.575F,0.03F,0.97F);
        txt_no.setBackground(col);
        txt_nom.setBackground(col);
        
        lbl_error.setForeground(Color.RED);
        
        //disable all input components on start
        txt_no.setEnabled(false);
        txt_nom.setEnabled(false);
        
        //table
        col_1 = table.getColumnModel().getColumn(1);
        col_2 = table.getColumnModel().getColumn(2);
        col_1.setCellEditor(new DefaultCellEditor(combo_type));
        col_2.setCellEditor(new DefaultCellEditor(txt_tab_nom));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //add ActionListeners
        table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
        btn_creer.addActionListener(new BtnCreateListener());
        btn_enregistrer.addActionListener(new BtnSaveListener());
        btn_quitter.addActionListener(new BtnQuitListener());
        list_villes.addListSelectionListener(new ListListener());
        
        col_1.getCellEditor().addCellEditorListener(new TypeComboListener());
        col_2.getCellEditor().addCellEditorListener(new TxtTabListener());
    }
    
    
    
    
    private class TxtTabListener implements CellEditorListener{

        private void TextChanged(){ 
            int row_index = table.getSelectedRow();
        
            if(row_index != -1){
                
            if(table.getValueAt(row_index,2)!= null && table.getValueAt(row_index,0)!=null){
                combo_type.setEnabled(false);
                combo_type.setEditable(false);
                txt_nom.setEnabled(true);
                
                String desc = table.getValueAt(row_index, 1).toString();
                int key = Integer.parseInt(table.getValueAt(row_index , 0).toString());
                if(desc.equals("Client")){
                    client = Globals.map_clients.get(key);
                    client.setNomCompte(table.getValueAt(row_index, 2).toString());
                    fournisseur = null;
                    Globals.map_clients.put(client.getNoCompte(), client);
                    MyFunctions.AddData(Globals.FileName.Clients, Globals.map_clients);
                }
                if(desc.equals("Fournisseur")){
                    fournisseur = Globals.map_fournisseurs.get(key);
                    fournisseur.setNomCompte(table.getValueAt(row_index, 2).toString());
                    client = null;
                    Globals.map_fournisseurs.put(fournisseur.getNoCompte(), fournisseur);
                    MyFunctions.AddData(Globals.FileName.Fournisseurs, Globals.map_fournisseurs);
                }
                error_row_nb = -1;
                
            } else {
                if(table.getValueAt(row_index, 2)== null){
                    lbl_error.setForeground(Color.red);
                    lbl_error.setText("Name can not be null.");
                    error_row_nb = row_index;
                }
            }
                
            
            }//end if row_index != -1}
        }
        
        @Override
        public void editingStopped(ChangeEvent e) {
            TextChanged();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            TextChanged();
        }
    
    }





    private class TypeComboListener implements CellEditorListener{

        private void CheckTypeChange(){
            int row_index = table.getSelectedRow();
            if(row_index != -1){
                
            if(table.getValueAt(row_index,1)!= null && table.getValueAt(row_index,0)==null){
                combo_type.setEnabled(false);
                combo_type.setEditable(false);
                txt_tab_nom.setEnabled(true);
                
                //setting solde to default : 0
                table.setValueAt(0.00, row_index, 3);
                
                //setting no compte
                table.setValueAt(Compte.getNoSerie(), row_index, 0);
                
                String desc = table.getValueAt(row_index, 1).toString();
                if(desc.equals("Client")){
                    table.setValueAt("Client "+Compte.getNoSerie(), row_index, 2);
                    client = new Client("Client "+Compte.getNoSerie(), ville);
                    fournisseur = null;
                    Globals.map_clients.put(client.getNoCompte(), client);
                    MyFunctions.AddData(Globals.FileName.Clients, Globals.map_clients);
                }
                if(desc.equals("Fournisseur")){
                    table.setValueAt("Fournisseur "+Compte.getNoSerie(), row_index, 2);
                    fournisseur = new Fournisseur("Fournisseur "+Compte.getNoSerie(), ville);
                    client = null;
                    Globals.map_fournisseurs.put(fournisseur.getNoCompte(), fournisseur);
                    MyFunctions.AddData(Globals.FileName.Fournisseurs, Globals.map_fournisseurs);
                }
            } else {
                if(table.getValueAt(row_index, 0)== null){
                    combo_type.setEnabled(true);
                    combo_type.setEditable(true);
                    txt_tab_nom.setEnabled(false);
                }
            }
                
            
            }//end if row_index != -1

        }
        
        @Override
        public void editingStopped(ChangeEvent e) {
            CheckTypeChange();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            CheckTypeChange();
        }
        
    }
    
    
    
    
    
    private class TableSelectionListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row_index = table.getSelectedRow();
            
            if(error_row_nb != -1 && error_row_nb != row_index){
                //if the previous editing row has errors
                JOptionPane.showMessageDialog(null, "Please Fix previous errors before proceeding.", "Error not handled.", JOptionPane.WARNING_MESSAGE);
                table.setRowSelectionInterval(error_row_nb, error_row_nb);
            }
            else {
            if(row_index != -1){
            
            if(ville == null ){
                    JOptionPane.showMessageDialog(null, "No Ville from List is Selected.", "Error" ,JOptionPane.WARNING_MESSAGE);
                    table.clearSelection();
                    table.getCellEditor(row_index, 0).cancelCellEditing();
                    col_1.getCellEditor().stopCellEditing();
                    col_2.getCellEditor().stopCellEditing();
            } else{
              if(table.getValueAt(row_index, 0)!=null){
                  combo_type.setEnabled(false);
                  combo_type.setEditable(false);
                  int key = Integer.parseInt(table.getValueAt(row_index, 0).toString());
                  if(table.getValueAt(row_index, 1).toString().equals("Client")){
                      client = (Client)Globals.map_clients.get(key);
                      fournisseur = null;
                  }
                  if(table.getValueAt(row_index, 1).toString().equals("Fournisseur")){
                      fournisseur = (Fournisseur)Globals.map_fournisseurs.get(key);
                      client = null;
                  }
                  txt_tab_nom.setEnabled(true);
                  col_1.getCellEditor().stopCellEditing();
              }
              else {
                  client = null;
                  fournisseur = null;
                  txt_tab_nom.setEnabled(false);
                  col_2.getCellEditor().stopCellEditing();
                  combo_type.setEnabled(true);
                  combo_type.setEditable(true);
                  txt_tab_nom.setEnabled(false);
                  col_2.getCellEditor().stopCellEditing();
              }
        
            }//end if ville != null
            
            }//end if row_index != -1
        
            }//end if previous row had errors
    
        }
    }
    
    
    
    
    
    private void RefreshTable(){
        tableModel = MyFilter.FilterComptesByVille(tableModel,ville);
        table.setModel(tableModel);
    }
    
    
    
    
    
    public void RefreshList(){
        //get villes list
        mod_villes = new DefaultListModel();
        if(!Globals.map_villes.isEmpty()){
            int i=0;
            Iterator it = Globals.map_villes.entrySet().iterator();
            
            while(it.hasNext()){
                Map.Entry<Integer,Ville> pair = (Map.Entry<Integer,Ville>)it.next();
                mod_villes.add(i,pair.getValue());
                list_villes.setModel(mod_villes);
                i++;
            }

        }
    }
    
    
    
    
    
    private class BtnSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                
                try{
                    //get the date chosen
                    String currentnb = Ville.getNoSerie()+"";
                    String str_nom = txt_nom.getText()+"";
                    if(str_nom.equals("")){
                        lbl_error.setText("Name can not be empty.");
                    }else{
                    
                    if(currentnb.equals(txt_no.getText())){
                        //if new Ville
                        ville = new Ville(txt_nom.getText());
                    }
                    else {
                        //if old ville
                        ville = (Ville) Globals.map_villes.get(Integer.parseInt(txt_no.getText().toString()));
                        ville.setNomVille(txt_nom.getText().toString());
                    }
                    Globals.map_villes.put(ville.getnoVille(), ville);
                    
                    MyFunctions.AddSortedDataVilles();
                    Globals.getFileVilles();
                    RefreshList();
                    int index = mod_villes.indexOf(Globals.map_villes.get(ville.getnoVille()));
                    list_villes.setSelectedIndex(index);
                    list_villes.revalidate();
                    //show success message
                    lbl_error.setForeground(Color.GREEN);
                    lbl_error.setText("The data was successfully saved.");
                    
                }
                }
                catch(Exception ex2){
                    System.out.println("Error:"+ex2);    
                }
                
            }
    }
    
    
    
    
    
    private class ListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            lbl_error.setText("");
            //set values of Ville
            if(list_villes.getSelectedIndex() != -1){
                ville = (Ville) list_villes.getSelectedValue();
                txt_no.setText(""+ville.getnoVille());
                txt_nom.setText(""+ville.getNomVille());
                txt_nom.setEnabled(true);
                txt_nom.setBackground(Color.white);
                RefreshTable();
                
            } else { ville = null; }
        }
    }
    
    
    
    
    
    private class BtnQuitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int quit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION ,JOptionPane.WARNING_MESSAGE);
                
                if(quit == JOptionPane.YES_OPTION){
                    data_menu.setPanel(new JPanel());
                }
                quit = 0;
        }
    }
    
    
    
    
    
    private class BtnCreateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_error.setText("");
            
            String noSerie = ""+Ville.getNoSerie();
            txt_no.setText(noSerie);
            txt_nom.setText("");
            txt_nom.setEnabled(true);
            txt_nom.setBackground(Color.WHITE);
            txt_nom.requestFocusInWindow();
            list_villes.setSelectedIndex(-1);
            list_villes.revalidate();
        }
    }
    
    
    
    
    
}


