package gestiondetransactionscommerciales;


import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.CENTER;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class InputPanelFournisseurs extends JPanel{

    
    private JPanel pan_north,pan_south, pan_left, pan_right, pan_in_data, pan_list, pan_in_cont, pan_radio_btns ;
    private JButton btn_creer, btn_enregistrer, btn_quitter;
    private String str_title;
    private String [] a_tab_headers;
    private DataMenu data_menu;
    private TitledBorder border;
    
    private JLabel lbl_no, lbl_nom, lbl_ville, lbl_solde, lbl_etat_compte, lbl_table;
    private JTextField txt_no, txt_nom, txt_solde;
    private ButtonGroup group;
    private JRadioButton rad_actif, rad_ferme, rad_suspendu;
    private JComboBox<Ville> combo_villes;
    
    private JLabel lbl_error;

    private JTextField txt_date, txt_montant;
    private JComboBox<Transaction.Description> combo_type;

    private Compte.EtatCompte etat_compte;
    private Transaction.Description type_trs;
    private LocalDate date;
    private Double montant = 0.0;
    
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scroll;
    private TableColumn col_2, col_3, col_4;
    
    private Fournisseur fournisseur;
    private TransactionFournisseur trsFournisseur;
    
    
    private JList<Fournisseur> list_fournisseurs;
    private DefaultListModel mod_fournisseurs;
    
    public InputPanelFournisseurs(DataMenu data_menu){
        Globals.getFileFournisseur();
        Globals.getFilePaiements();
        Globals.getFileAchats();
        
        
        //assignments
        pan_north = new JPanel();
        pan_south = new JPanel();
        pan_left = new JPanel();
        pan_right = new JPanel();
        pan_in_data = new JPanel();
        pan_list = new JPanel();
        pan_in_cont = new JPanel();
        pan_radio_btns = new JPanel();
        
        btn_creer = new JButton("Creer");
        btn_enregistrer = new JButton("Enregistrer");
        btn_quitter = new JButton("Quitter");

                
        lbl_no = new JLabel("No Fournisseur", SwingConstants.RIGHT);
        lbl_nom = new JLabel("Nom Fournisseur", SwingConstants.RIGHT);
        lbl_ville = new JLabel("Ville", SwingConstants.RIGHT);
        lbl_solde = new JLabel("Solde", SwingConstants.RIGHT);
        lbl_etat_compte = new JLabel("Mode Paiement", SwingConstants.RIGHT);
        
        lbl_table = new JLabel("Transactions Fournisseur");
        
        txt_no = new JTextField(19);
        txt_nom = new JTextField(19);
        txt_solde = new JTextField(19);
        
        Border txt_border = txt_no.getBorder();
        Border actual_txt_border = BorderFactory.createCompoundBorder(txt_border, BorderFactory.createEmptyBorder(2, 0, 2, 0));
        txt_no.setBorder(actual_txt_border);
        txt_nom.setBorder(actual_txt_border);
        txt_solde.setBorder(actual_txt_border);
        
        txt_date = new JTextField();
        txt_montant = new JTextField();
                
        lbl_error = new JLabel();
        
        
        list_fournisseurs = new JList();
        mod_fournisseurs = new DefaultListModel();
        
        rad_actif = new JRadioButton("Actif");        
        rad_ferme = new JRadioButton("Ferme");   
        rad_suspendu = new JRadioButton("Suspendu");    
        
        combo_villes = Globals.getVillesCombo();
        
        group = new ButtonGroup();
        group.add(rad_actif);
        group.add(rad_ferme);
        group.add(rad_suspendu);
        
        combo_type = new JComboBox();
        combo_type.addItem(Transaction.Description.Achat);
        combo_type.addItem(Transaction.Description.Paiement);
        
                
        //Data assignments
        this.data_menu = data_menu;
        etat_compte = Compte.EtatCompte.ACTIF;
        str_title = "Fournisseurs";
        border = BorderFactory.createTitledBorder(null, "Infos "+str_title, CENTER, 0);
        
        a_tab_headers = new String[]{"No Trs","Type Trs","Date Trs","Montant"};
        tableModel = new DefaultTableModel(a_tab_headers,20){
                @Override
                public boolean isCellEditable(int row, int col) {
                     switch (col) {
                         case 1:
                         case 2:
                         case 3:
                             return true;
                         default:
                             return false;
                      }
                }
            };
        
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);

        
                
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
       
        Font font = new Font("Verdana", Font.PLAIN, 9);
        rad_actif.setFont(font);
        rad_ferme.setFont(font);
        rad_suspendu.setFont(font);
        
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
        
        gbc.gridy = 3;
        pan_in_data.add(lbl_ville,gbc);
        
        gbc.gridy = 4;
        pan_in_data.add(lbl_solde,gbc);
        
        gbc.gridy = 5;
        pan_in_data.add(lbl_etat_compte, gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        
        gbc.gridy = 1;
        pan_in_data.add(txt_no,gbc);
        
        gbc.gridy = 2;
        pan_in_data.add(txt_nom,gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 3;
        pan_in_data.add(combo_villes,gbc);
        
        
        gbc.gridy = 4;
        pan_in_data.add(txt_solde,gbc);
        
        
        gbc.gridy = 5;
        pan_in_data.add(pan_radio_btns,gbc);
        
        
        pan_radio_btns.add(rad_actif);
        pan_radio_btns.add(rad_ferme);
        pan_radio_btns.add(rad_suspendu);
        
        
        pan_list.setBorder(border);
        pan_list.setBackground(Color.white);
        pan_list.add(list_fournisseurs);
        
        
        Color col = Color.getHSBColor(0.575F,0.03F,0.97F);
        txt_no.setBackground(col);
        txt_nom.setBackground(col);
        txt_solde.setBackground(col);
        combo_villes.setBackground(col);
        
        lbl_error.setForeground(Color.RED);
        
        
        //disable all input components on start
        txt_no.setEnabled(false);
        txt_nom.setEnabled(false);
        txt_solde.setEnabled(false);
        
        txt_montant.setEnabled(false);
        combo_villes.setEnabled(false);
        rad_actif.setEnabled(false);
        rad_ferme.setEnabled(false);
        rad_suspendu.setEnabled(false);
        txt_date.setEnabled(false);
        
        group.setSelected(rad_actif.getModel(), true);
        
        
        //table
        col_2 = table.getColumnModel().getColumn(1);
        col_3 = table.getColumnModel().getColumn(2);
        col_4 = table.getColumnModel().getColumn(3);
        
        col_2.setCellEditor(new DefaultCellEditor(combo_type));
        col_3.setCellEditor(new DefaultCellEditor(txt_date));
        col_4.setCellEditor(new DefaultCellEditor(txt_montant));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
        //get fournisseur list
        if(!Globals.map_fournisseurs.isEmpty()){
            int i=0;
            Iterator it = Globals.map_fournisseurs.entrySet().iterator();
            
            while(it.hasNext()){
                Map.Entry<Integer,Fournisseur> pair = (Map.Entry<Integer,Fournisseur>)it.next();
                mod_fournisseurs.add(i,pair.getValue());
                list_fournisseurs.setModel(mod_fournisseurs);
                i++;
            }

        }
        
        
        
        //add ActionListeners
        
        table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
        
        
        list_fournisseurs.addListSelectionListener(new ListListener());        
        col_2.getCellEditor().addCellEditorListener(new TypeComboListener());

        col_3.getCellEditor().addCellEditorListener(new DateTextListener());
        col_4.getCellEditor().addCellEditorListener(new MontantTextListener());
        
        rad_actif.addActionListener(new RadioButtonListener());
        rad_ferme.addActionListener(new RadioButtonListener());
        rad_suspendu.addActionListener(new RadioButtonListener());
        
        btn_creer.addActionListener(new BtnCreateListener());
        btn_enregistrer.addActionListener(new BtnSaveListener());
        btn_quitter.addActionListener(new BtnQuitListener());
    }
    
    
    
    
    private class MontantTextListener implements CellEditorListener{

        public void checkErrorMontant(){
            if(trsFournisseur != null && trsFournisseur.getDesc().equals(Transaction.Description.Paiement)){
            try{
                int row_index = table.getSelectedRow();
                
                Double old_montant = montant;
                montant = Double.parseDouble(table.getValueAt(row_index, 3).toString());
                
                trsFournisseur.UndoMiseAJourCompte(old_montant);
                trsFournisseur.miseAJourCompte(montant);
                Globals.map_paiements.get(trsFournisseur.getNoTrans()).UndoMiseAJourCompte(old_montant);
                Globals.map_paiements.get(trsFournisseur.getNoTrans()).miseAJourCompte(montant);
                MyFunctions.AddSortedDataPaiements();
                
                lbl_error.setText("");
            }
            catch(NumberFormatException exc){
                lbl_error.setForeground(Color.red);
                lbl_error.setText("Montant must be a number.");
                table.setValueAt(trsFournisseur.getMontant(), table.getSelectedRow(), 3);
            }
            }
        }
        

        @Override
        public void editingStopped(ChangeEvent e) {
            checkErrorMontant();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            checkErrorMontant();
        }
    
    }
    
    
    
    
        
    private class DateTextListener implements CellEditorListener{

        public void CheckDateError(String txt){
            if(trsFournisseur!=null){
            try{
                //int row_index = table.getSelectedRow();
                String str_date = txt;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(str_date, formatter);
                lbl_error.setText("");
                trsFournisseur.setDateTrans(date);
                
                if(trsFournisseur.getDesc().equals(Transaction.Description.Achat)){
                    Globals.map_achats.get(trsFournisseur.getNoTrans()).setDateTrans(date);
                    MyFunctions.AddSortedDataAchats();
                }
                
                if(trsFournisseur.getDesc().equals(Transaction.Description.Paiement)){
                    Globals.map_paiements.get(trsFournisseur.getNoTrans()).setDateTrans(date);
                    MyFunctions.AddSortedDataPaiements();
                }
            }
            catch(DateTimeParseException e){
                lbl_error.setForeground(Color.red);
                lbl_error.setText("Dates must have this format : yyyy-MM-dd");
                table.setValueAt(trsFournisseur.getDateTrans(), table.getSelectedRow(), 2);
            }
            
            }
        }

        @Override
        public void editingStopped(ChangeEvent e) {
            String txt = table.getValueAt(table.getSelectedRow(),2).toString();
            CheckDateError(txt);
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            String txt = e.getSource().toString();
            CheckDateError(txt);
        }

    }

    
    
    
    
     
    private class TypeComboListener implements CellEditorListener{


        private void CheckComboChange(){
            int row_index = table.getSelectedRow();
            if(row_index!=-1){
                if(table.getValueAt(row_index, 0)!=null){
                    lbl_error.setForeground(Color.red);
                    lbl_error.setText("Type can not be changed once set!");
                } else {
                    lbl_error.setText("");
                }
            try{
                String desc = ""+table.getValueAt(row_index, 1).toString();
                
                
                if(desc != "" && table.getValueAt(row_index, 0)==null){
                    if(desc.equals(Transaction.Description.Achat.toString())){
                     
                        type_trs = Transaction.Description.Achat;
                        
                        txt_date.setEnabled(true);
                        txt_montant.setText("0.0");
                        
                        //get the date chosen
                        String str_date = ""+txt_date.getText();
                        
                        if(str_date.equals("")){
                            str_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            table.setValueAt(str_date, row_index, 2);
                        }
                        
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(str_date, formatter);
                            
                        trsFournisseur = new Achat(date,0.0,fournisseur);
                        Globals.map_achats.put(trsFournisseur.getNoTrans(), (Achat)trsFournisseur);
                            
                        //sort again
                        MyFunctions.AddSortedDataAchats();
                        Globals.getFileAchats();
                            
                        //set trs no in table
                        table.setValueAt(trsFournisseur.getNoTrans(), row_index, 0);
                        table.setValueAt(0.00, row_index, 3);
                        
                        txt_montant.setEnabled(false);
                    }
                   
                    
                if(desc.equals(Transaction.Description.Paiement.toString())){
                    type_trs = Transaction.Description.Paiement;
                    
                    txt_date.setEnabled(true);
                    txt_montant.setText("0.0");
                    
                    //get the date chosen
                    String str_date = ""+txt_date.getText();
                            if(str_date.equals("")){
                                str_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                table.setValueAt(str_date, row_index, 2);
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate date = LocalDate.parse(str_date, formatter);
                            
                            table.setValueAt(0.0, row_index, 3);
                            
                            trsFournisseur = new Paiement(date,0.0,fournisseur);
                            Globals.map_paiements.put(trsFournisseur.getNoTrans(), (Paiement)trsFournisseur);
                            
                            //sort again
                            MyFunctions.AddSortedDataPaiements();
                            Globals.getFilePaiements();
                            
                            //set trs no in table
                            table.setValueAt(trsFournisseur.getNoTrans(), row_index, 0);
                            
                            txt_montant.setEnabled(true);
                    }
                        
                }
            }
            catch(java.lang.NullPointerException exx){
                    txt_date.setEnabled(false);
                    txt_montant.setEnabled(false);
                    txt_montant.setText("");
            }
                    
                    
            }
        }

        @Override
        public void editingStopped(ChangeEvent e) {
            CheckComboChange();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            CheckComboChange();
        }
    }
    
    
    
    
    
    
    
    
        
    private class ListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            lbl_error.setText("");
            //set values of achat
            if(list_fournisseurs.getSelectedIndex() != -1){
                fournisseur = (Fournisseur) list_fournisseurs.getSelectedValue();
                txt_no.setText(""+fournisseur.getNoCompte());
                txt_nom.setText(""+fournisseur.getNomCompte());
                
                combo_villes.setSelectedIndex(fournisseur.getVilleCompte().getnoVille());
                combo_villes.revalidate();
                
                txt_solde.setText(""+fournisseur.getSolde());
                
                if(fournisseur.getEtatCompte().equals(Compte.EtatCompte.ACTIF)){group.setSelected(rad_actif.getModel(), true);}
                if(fournisseur.getEtatCompte().equals(Compte.EtatCompte.FERME)){group.setSelected(rad_ferme.getModel(), true);}
                if(fournisseur.getEtatCompte().equals(Compte.EtatCompte.SUSPENDU)){group.setSelected(rad_suspendu.getModel(), true);}
                
                txt_nom.setEnabled(true);
                txt_nom.setBackground(Color.white);
                
                combo_villes.setEnabled(true);
                combo_villes.setBackground(Color.WHITE);
                
                rad_actif.setEnabled(true);
                rad_ferme.setEnabled(true);
                rad_suspendu.setEnabled(true);
                
                RefreshTable();
                
            } else { fournisseur = null; }
        }
    }
    
    
    
    
    
    
    private void RefreshTable(){
        tableModel = MyFilter.FilterTransactionFournisseurByFournisseur(tableModel,fournisseur);
        table.setModel(tableModel);
    }
    
    
    
    
    
        
    private class BtnSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String currentnb = Compte.getNoSerie()+"";
            //check if all values are not null
            if(txt_nom.getText()!="" && combo_villes.getSelectedIndex()!=0){
                   
                if(currentnb.equals(txt_no.getText())){
                    //if new fournisseur
                    fournisseur = new Fournisseur(txt_nom.getText(), (Ville)combo_villes.getSelectedItem());
                    fournisseur.setEtat(etat_compte);
                    Globals.map_fournisseurs.put(fournisseur.getNoCompte(),fournisseur);
                    Globals.setSerialNbs();
                      
                    for(int i=0; i<mod_fournisseurs.getSize(); i++){
                        if(mod_fournisseurs.getElementAt(i).toString().equals(fournisseur.toString())){
                            list_fournisseurs.setSelectedIndex(i);
                            fournisseur = (Fournisseur) list_fournisseurs.getSelectedValue();
                            break;
                        }
                    }
                  
                }
                else {
                    //if old fournisseur
                    Globals.map_fournisseurs.get(fournisseur.getNoCompte()).setEtat(etat_compte);
                    Globals.map_fournisseurs.get(fournisseur.getNoCompte()).setNomCompte(txt_nom.getText());
                    Globals.map_fournisseurs.get(fournisseur.getNoCompte()).setVille((Ville) combo_villes.getSelectedItem());
                    fournisseur = Globals.map_fournisseurs.get(fournisseur.getNoCompte());
                }
                    
                //sort again
                ArrayList<Fournisseur> arr_sort = new ArrayList();
                    
                Iterator it = Globals.map_fournisseurs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer,Fournisseur> pair = (Map.Entry<Integer,Fournisseur>)it.next();
                    arr_sort.add(pair.getValue());
                }
                    
                Collections.sort(arr_sort, Comparators.CompteComparator());
                    
                Globals.map_fournisseurs = new LinkedHashMap();
                    
                mod_fournisseurs = new DefaultListModel();
                    
                int i=0;
                    
                it = arr_sort.iterator();
        
                while(it.hasNext()){
                    Fournisseur c = (Fournisseur)it.next();
                    Globals.map_fournisseurs.put(c.getNoCompte(),c);
                    mod_fournisseurs.add(i,c);
                    i++;
                }
                    
                    
                MyFunctions.AddData(Globals.FileName.Fournisseurs, Globals.map_fournisseurs);
                Globals.getFileFournisseur();

                list_fournisseurs.setModel(mod_fournisseurs);
                list_fournisseurs.revalidate();
                    
                
                //show success message
                lbl_error.setForeground(Color.GREEN);
                lbl_error.setText("The data was successfully saved.");
                    
            } else {
                JOptionPane.showMessageDialog(null, "Fill in all fields before saving.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
            
    }

    

    
    
    
    
       
    private class TableSelectionListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row_index = table.getSelectedRow();
            
            if(fournisseur == null){
                if(!table.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(null, "No Fournisseur from List is Selected", "Error" ,JOptionPane.WARNING_MESSAGE);
                    table.clearSelection();
                    
                    table.getCellEditor(row_index, 1).cancelCellEditing();
                    table.getCellEditor(row_index, 2).cancelCellEditing();
                    table.getCellEditor(row_index, 3).cancelCellEditing();
                }
            } else{
              if(row_index !=-1){
                
              if(table.getValueAt(row_index, 1)!=null){
                  txt_date.setEnabled(true);
                  if(table.getValueAt(row_index, 1).equals(Transaction.Description.Achat.toString())){ 
                      type_trs = Transaction.Description.Achat;
                      //montant of achat can not be changed
                      txt_montant.setEnabled(false);
                  }
                  if(table.getValueAt(row_index, 1).equals(Transaction.Description.Paiement.toString())){ 
                      type_trs = Transaction.Description.Paiement;
                      //montant of Paiement can be changed
                      txt_montant.setEnabled(true);
                  }
                  col_2.getCellEditor().stopCellEditing();
                  combo_type.setEnabled(false);
                  combo_type.setEditable(false);
              }
              else {
                  txt_date.setEnabled(false);
                  txt_montant.setEnabled(false);
                  type_trs = null;
                  combo_type.setEditable(true);
                  combo_type.setEnabled(true);
              }
              
              if(type_trs!=null){
                  if(table.getValueAt(row_index, 2)!=null){
                      try{
                            String str_date = table.getValueAt(row_index, 2).toString();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            date = LocalDate.parse(str_date, formatter);
                            lbl_error.setText("");
                      }
                      catch(DateTimeParseException ex){
                          lbl_error.setText("Dates must have this format : yyyy-MM-dd");
                      }
                  } else {
                      date = LocalDate.now();
                      table.setValueAt(date, row_index, 2);
                  }
                  
                  if(type_trs.equals(Transaction.Description.Achat)){
                      montant = 0.0;
                      trsFournisseur = Globals.map_achats.get(Integer.parseInt(table.getValueAt(row_index, 0).toString()));
                      lbl_error.setText("");
                  }
                  if(type_trs.equals(Transaction.Description.Paiement)){
                      try{
                          if(table.getValueAt(row_index, 3) != ""){
                            montant = Double.parseDouble(table.getValueAt(row_index, 3).toString());
                          }
                          trsFournisseur = Globals.map_paiements.get(Integer.parseInt(table.getValueAt(row_index, 0).toString()));
                          lbl_error.setText("");
                      }
                      catch(NumberFormatException ex2){
                          lbl_error.setForeground(Color.red);
                          lbl_error.setText("Montant must be a number.");
                      }
                  }
                  
              } else {
                  trsFournisseur = null;
              }
                
                }
              }
           
        }
        
    }
    
    
    
        
    
    
    
    private class RadioButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source.equals(rad_actif)) { etat_compte =Compte.EtatCompte.ACTIF;}
        if(source.equals(rad_ferme)) { etat_compte =Compte.EtatCompte.FERME;}
        if(source.equals(rad_suspendu)) { etat_compte =Compte.EtatCompte.SUSPENDU;}
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    private class BtnCreateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_error.setText("");
            
            String noSerie = ""+Compte.getNoSerie();
            txt_no.setText(noSerie);
            txt_nom.setText("");
            combo_villes.setSelectedIndex(0);
            group.setSelected(rad_actif.getModel(), true);
            txt_solde.setText("0.0");
            
            txt_nom.setEnabled(true);
            txt_nom.setBackground(Color.WHITE);
            combo_villes.setEnabled(true);
            combo_villes.setBackground(Color.WHITE);
            
            rad_actif.setEnabled(true);
            rad_ferme.setEnabled(true);
            rad_suspendu.setEnabled(true);
            
            txt_nom.requestFocusInWindow();
            list_fournisseurs.setSelectedIndex(-1);
        }
    }
    
    
    
    
    
       
    
    
    private class BtnQuitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int quit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION ,JOptionPane.WARNING_MESSAGE);
                
                if(quit == JOptionPane.YES_OPTION){
                    data_menu.setPanel(new JPanel());
                }
        }
    }
    
    
    
}
