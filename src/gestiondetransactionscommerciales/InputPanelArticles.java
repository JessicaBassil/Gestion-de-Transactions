package gestiondetransactionscommerciales;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
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

public class InputPanelArticles extends JPanel{
    
    private JPanel pan_north,pan_south, pan_left, pan_right, pan_in_data, pan_combo, pan_list, pan_in_cont ;
    private JButton btn_creer, btn_enregistrer, btn_quitter;
    private String str_title;
    private String [] a_tab_headers;
    private DataMenu data_menu;
    private TitledBorder border;
    private JLabel lbl_no, lbl_nom, lbl_qte, lbl_prix, lbl_cout, lbl_profit, lbl_cat_art, lbl_table;
    private JTextField txt_no, txt_nom, txt_qte, txt_prix, txt_cout, txt_profit;
    private JLabel lbl_error;
    
    private JComboBox<CategorieArticle> combo_cat_art;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scroll;

        
    private JList<Article> list_articles;
    private DefaultListModel mod_articles;
    
    private TableColumn col_1, col_2, col_3, col_4;
    private JComboBox<Transaction.Description> combo_type;
    private JTextField txt_tab_date, txt_tab_qte;
    
    private CategorieArticle cat_art;
    private Article article;
    
    private DetailVente detail_vente;
    private DetailAchat detail_achat;
    private Vente vente;
    private Achat achat;
    private Client client;
    private Fournisseur fourn;
    private JComboBox<Fournisseur> combo_fourn;
    private JComboBox<Client> combo_clients;
    
    
    
    
    
    public InputPanelArticles(DataMenu data_menu) {
        Globals.getFileArticles();
        Globals.getFileCategorieArticle();
        Globals.getFileDetailAchat();
        Globals.getFileDetailVente();
        Globals.getFileVentes();
        Globals.getFileAchats();
        Globals.getFileClients();
        Globals.getFileFournisseur();
        
                
        //assignments
        pan_north = new JPanel();
        pan_south = new JPanel();
        pan_left = new JPanel();
        pan_right = new JPanel();
        pan_in_data = new JPanel();
        pan_combo = new JPanel();
        pan_list = new JPanel();
        pan_in_cont = new JPanel();
        
        btn_creer = new JButton("Creer");
        btn_enregistrer = new JButton("Enregistrer");
        btn_quitter = new JButton("Quitter");
        
        lbl_no = new JLabel("No Article", SwingConstants.RIGHT);
        lbl_nom = new JLabel("Nom Article", SwingConstants.RIGHT);
        lbl_qte = new JLabel("Quantite Stock",SwingConstants.RIGHT);
        lbl_prix = new JLabel("Prix Vente", SwingConstants.RIGHT);
        lbl_cout = new JLabel("Cout Achat",SwingConstants.RIGHT);
        lbl_profit = new JLabel("Profit", SwingConstants.RIGHT);
        
        lbl_cat_art = new JLabel("Choisir categorie", SwingConstants.CENTER);
        lbl_table = new JLabel("Transactions Article");
        
        txt_no = new JTextField(19);
        txt_nom = new JTextField(19);
        txt_qte = new JTextField(19);
        txt_prix = new JTextField(19);
        txt_cout = new JTextField(19);
        txt_profit = new JTextField(19);
        
        Border txt_border = txt_no.getBorder();
        Border actual_txt_border = BorderFactory.createCompoundBorder(txt_border, BorderFactory.createEmptyBorder(2, 0, 2, 0));
        txt_no.setBorder(actual_txt_border);
        txt_nom.setBorder(actual_txt_border);
        txt_qte.setBorder(actual_txt_border);
        txt_prix.setBorder(actual_txt_border);
        txt_cout.setBorder(actual_txt_border);
        txt_profit.setBorder(actual_txt_border);
        
        txt_tab_date = new JTextField();
        txt_tab_qte = new JTextField();
        
        lbl_error = new JLabel();
        
        combo_clients = new JComboBox();
        combo_fourn = new JComboBox();
        combo_cat_art = new JComboBox();
        combo_type = new JComboBox();
        list_articles = new JList();
        mod_articles = new DefaultListModel();
        
                
        //Data assignments
        this.data_menu = data_menu;
        str_title = "Articles";
        border = BorderFactory.createTitledBorder(null, "Infos "+str_title, CENTER, 0);
        
        a_tab_headers = new String[]{"No Trs","Type Trs","Client / Fournisseur","Date Trs","Quantité","Prix/Cout","Total"};
        tableModel = new DefaultTableModel(a_tab_headers,20){
                @Override
                public boolean isCellEditable(int row, int col) {
                     switch (col) {
                         case 1:
                         case 2:
                         case 3:
                         case 4:
                             return true;
                         default:
                             return false;
                      }
                }
            };
        
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);
        
        
        
        //add values to combo boxes
        combo_clients = MyFunctions.AddDataToComboBox(Globals.map_clients);
        combo_clients.revalidate();
        combo_fourn = MyFunctions.AddDataToComboBox(Globals.map_fournisseurs);
        combo_fourn.revalidate();
        combo_cat_art = MyFunctions.AddDataToComboBox(Globals.map_categoriesArticles);
        combo_cat_art.revalidate();
        
        combo_type.addItem(Transaction.Description.Vente);
        combo_type.addItem(Transaction.Description.Achat);
        
        
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
        pan_right.add(pan_combo, BorderLayout.NORTH);
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
        
        gbc.gridy = 3;
        pan_in_data.add(lbl_qte,gbc);
        
        gbc.gridy = 4;
        pan_in_data.add(lbl_prix,gbc);
        
        gbc.gridy = 5;
        pan_in_data.add(lbl_cout,gbc);
        
        gbc.gridy = 6;
        pan_in_data.add(lbl_profit,gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        
        gbc.gridy = 1;
        pan_in_data.add(txt_no,gbc);
        
        gbc.gridy = 2;
        pan_in_data.add(txt_nom,gbc);
        
        gbc.gridy = 3;
        pan_in_data.add(txt_qte,gbc);
        
        gbc.gridy = 4;
        pan_in_data.add(txt_prix,gbc);
        
        gbc.gridy = 5;
        pan_in_data.add(txt_cout,gbc);
        
        gbc.gridy = 6;
        pan_in_data.add(txt_profit,gbc);
        
        pan_combo.setLayout(new GridLayout(1,2,10,10));
        pan_combo.add(lbl_cat_art);
        pan_combo.add(combo_cat_art);
        
        pan_list.setBorder(border);
        pan_list.setBackground(Color.white);
        pan_list.add(list_articles);
        
        
        Color col = Color.getHSBColor(0.575F,0.03F,0.97F);
        txt_no.setBackground(col);
        txt_nom.setBackground(col);
        txt_qte.setBackground(col);
        txt_prix.setBackground(col);
        txt_cout.setBackground(col);
        txt_profit.setBackground(col);
        
        lbl_error.setForeground(Color.RED);

        //disable all input components on start
        txt_no.setEnabled(false);
        txt_nom.setEnabled(false);
        txt_qte.setEnabled(false);
        txt_prix.setEnabled(false);
        txt_cout.setEnabled(false);
        txt_profit.setEnabled(false);

        
        //table
        col_1 = table.getColumnModel().getColumn(1);
        col_2 = table.getColumnModel().getColumn(2);
        col_3 = table.getColumnModel().getColumn(3);
        col_4 = table.getColumnModel().getColumn(4);
        
        col_1.setCellEditor(new DefaultCellEditor(combo_type));
        col_2.setCellEditor(new DefaultCellEditor(combo_clients));
        col_3.setCellEditor(new DefaultCellEditor(txt_tab_date));
        col_4.setCellEditor(new DefaultCellEditor(txt_tab_qte));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
        
        
        //adding Listeners
        table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
        col_1.getCellEditor().addCellEditorListener(new ComboTypeListener());
        col_2.getCellEditor().addCellEditorListener(new ComboCliFournListener());
        col_3.getCellEditor().addCellEditorListener(new CellDateListener());
        col_4.getCellEditor().addCellEditorListener(new CellQteListener());
        
        
        btn_quitter.addActionListener(new BtnQuitListener());
        btn_creer.addActionListener(new BtnCreateListener());
        btn_enregistrer.addActionListener(new BtnSaveListener());
        
        list_articles.addListSelectionListener(new ListListener());
        combo_cat_art.addActionListener(new CatArtComboListener());
    }
    
    
    
    
    
    private class CellDateListener implements CellEditorListener{
        
        private void CheckTextChange(){
            int row_index = table.getSelectedRow();
            
            if(row_index != -1){
                if(table.getValueAt(row_index, 3)!= null){
                    try{
                        String str_date = table.getValueAt(row_index, 3).toString();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(str_date, formatter);
                        lbl_error.setText("");
                        
                        String desc = table.getValueAt(row_index, 1).toString();
                        int no_trs = Integer.parseInt(table.getValueAt(row_index, 0).toString());
                        
                        if(desc.equals(Transaction.Description.Achat.toString())){
                            vente = null;
                            detail_vente = null;
                            achat = Globals.map_achats.get(no_trs);
                            detail_achat = Globals.map_detailsAchat.get(achat.getNoTrans()+","+article.getNoArticle());
                            achat.setDateTrans(date);
                            Globals.map_achats.put(achat.getNoTrans(), achat);
                            detail_achat.setAchat(achat);
                            Globals.map_detailsAchat.put(achat.getNoTrans()+","+article.getNoArticle(), detail_achat);
                            MyFunctions.AddSortedDataAchats();
                            MyFunctions.AddData(Globals.FileName.DetailAchat, Globals.map_detailsAchat);
                        }
                        if(desc.equals(Transaction.Description.Vente.toString())){
                            achat = null;
                            detail_achat = null;
                            vente = Globals.map_ventes.get(no_trs);
                            detail_vente = Globals.map_detailsVente.get(vente.getNoTrans()+","+article.getNoArticle());
                            vente.setDateTrans(date);
                            Globals.map_ventes.put(vente.getNoTrans(), vente);
                            detail_vente.setVente(vente);
                            Globals.map_detailsVente.put(vente.getNoTrans()+","+article.getNoArticle(), detail_vente);
                            MyFunctions.AddSortedDataVentes();
                            MyFunctions.AddData(Globals.FileName.DetailVente, Globals.map_detailsVente);
                        }
                    }catch(DateTimeParseException exx){
                       lbl_error.setForeground(Color.red);
                      lbl_error.setText("Dates must have this format : yyyy-MM-dd ");
                    }
                }
            }//end if row_index != -1
        }

        @Override
        public void editingStopped(ChangeEvent e) {
            CheckTextChange();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            CheckTextChange();
        }
    }
    
    
    
    
    
    private class CellQteListener implements CellEditorListener{

        private void CheckTextChange(){
        int row_index = table.getSelectedRow();
            
            if(row_index != -1){
                if(table.getValueAt(row_index, 4)!= null && Integer.parseInt(table.getValueAt(row_index, 4).toString())!= 0){
                    try{
                        String str_qte = table.getValueAt(row_index, 4).toString();
                        int qte = Integer.parseInt(str_qte);
                        lbl_error.setText("");
                        
                        String desc = table.getValueAt(row_index, 1).toString();
                        int no_trs = Integer.parseInt(table.getValueAt(row_index, 0).toString());
                        double nb_paie = Double.parseDouble(table.getValueAt(row_index, 5).toString());
                        if(desc.equals(Transaction.Description.Achat.toString())){
                            vente = null;
                            detail_vente = null;
                            achat = Globals.map_achats.get(no_trs);
                            detail_achat = Globals.map_detailsAchat.get(achat.getNoTrans()+","+article.getNoArticle());
                            detail_achat.setQuantite(qte);
                            Globals.map_detailsAchat.put(achat.getNoTrans()+","+article.getNoArticle(), detail_achat);
                            MyFunctions.AddData(Globals.FileName.DetailAchat, Globals.map_detailsAchat);
                        }
                        if(desc.equals(Transaction.Description.Vente.toString())){
                            achat = null;
                            detail_achat = null;
                            vente = Globals.map_ventes.get(no_trs);
                            detail_vente = Globals.map_detailsVente.get(vente.getNoTrans()+","+article.getNoArticle());
                            detail_vente.setQuantite(qte);
                            Globals.map_detailsVente.put(vente.getNoTrans()+","+article.getNoArticle(), detail_vente);
                            MyFunctions.AddData(Globals.FileName.DetailVente, Globals.map_detailsVente);                            
                        }
                        
                        table.setValueAt(qte*nb_paie, row_index, 6);
                    }catch(NumberFormatException eee){
                      lbl_error.setForeground(Color.red);
                      lbl_error.setText("Quantity must be an Integer!");
                    }
                
                }//end if qte not null
                
            }//end if row_index != -1
        
        }
        
        @Override
        public void editingStopped(ChangeEvent e) {
            CheckTextChange();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            CheckTextChange();
        }
    }
    
    
    
    
       
    private class ComboTypeListener implements CellEditorListener{


        private void CheckComboChange(){
            int row_index = table.getSelectedRow();
             
            if(row_index !=-1){
                
            if(table.getValueAt(row_index, 1)!=null){
                combo_type.setEnabled(false);
                combo_type.setEditable(false);
                combo_clients.setEnabled(true);
                combo_clients.setEnabled(true);
                combo_fourn.setEnabled(true);
                combo_fourn.setEditable(true);
                
                String desc = table.getValueAt(row_index, 1).toString();
            
                if(!"".equals(desc) && desc.equals(Transaction.Description.Vente.toString())){
                    //if vente was chosen
                    col_2.setCellEditor(new DefaultCellEditor(combo_clients));
                    col_2.getCellEditor().addCellEditorListener(new ComboCliFournListener());
                }
                    
                if(!"".equals(desc) && desc.equals(Transaction.Description.Achat.toString())){
                    //if achat was chosen
                    col_2.setCellEditor(new DefaultCellEditor(combo_fourn));
                    col_2.getCellEditor().addCellEditorListener(new ComboCliFournListener());
                }
            
            }else{
                combo_type.setEnabled(true);
                combo_type.setEditable(true);
                combo_clients.setEnabled(false);
                combo_fourn.setEnabled(false);
                combo_clients.setEditable(false);
                combo_fourn.setEditable(false);
            }    
            }//end if row_index != -1
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
    
    
    
    
    
    private class ComboCliFournListener implements CellEditorListener{

        private void CheckComboChange(){
            int row_index = table.getSelectedRow();
            if(row_index != -1){
                
            if(table.getValueAt(row_index,2)!= null && table.getValueAt(row_index,0)==null){
                combo_clients.setEnabled(false);
                combo_fourn.setEnabled(false);
                combo_clients.setEditable(false);
                combo_fourn.setEditable(false);
                txt_tab_date.setEnabled(true);
                txt_tab_qte.setEnabled(true);
                
                //setting date to default(now)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.now();
                String str_date = date.format(formatter);
                table.setValueAt(str_date, row_index, 3);
                
                //setting qte to default : 0
                table.setValueAt(0, row_index, 4);
                
                //setting no trs && total
                table.setValueAt(Transaction.getNoSerie(), row_index, 0);
                table.setValueAt(0, row_index, 6);
                    
                col_2.getCellEditor().addCellEditorListener(new ComboCliFournListener());
                
                String desc = table.getValueAt(row_index, 1).toString();
                if(desc.equals(Transaction.Description.Achat.toString())){
                    table.setValueAt(article.getCoutAchatParUnite(), row_index, 5);
                    col_2.setCellEditor(new DefaultCellEditor(combo_fourn));
                    
                    detail_vente = null;
                    vente = null;
                    client = null;
                    fourn = (Fournisseur) table.getValueAt(row_index,2);
                    achat = new Achat(date, article.getCoutAchatParUnite(), fourn);
                    detail_achat = new DetailAchat(achat, article, 0, article.getCoutAchatParUnite());
                    Globals.map_achats.put(achat.getNoTrans(), achat);
                    Globals.map_detailsAchat.put(achat.getNoTrans()+","+article.getNoArticle(), detail_achat);
                    MyFunctions.AddSortedDataAchats();
                    MyFunctions.AddData(Globals.FileName.DetailAchat, Globals.map_detailsAchat);
                }
                
                if(desc.equals(Transaction.Description.Vente.toString())){
                    table.setValueAt(article.getPrixVenteParUnite(), row_index, 5);
                    col_2.setCellEditor(new DefaultCellEditor(combo_clients));
                    
                    detail_achat = null;
                    achat = null;
                    fourn = null;
                    client = (Client) table.getValueAt(row_index, 2);
                    vente = new Vente(date, article.getPrixVenteParUnite(), client);
                    detail_vente = new DetailVente(vente, article, 0);
                    Globals.map_ventes.put(vente.getNoTrans(), vente);
                    Globals.map_detailsVente.put(vente.getNoTrans()+","+article.getNoArticle(), detail_vente);
                    MyFunctions.AddSortedDataVentes();
                    MyFunctions.AddData(Globals.FileName.DetailVente, Globals.map_detailsVente);
                }
                
                
            }
                
            
            }//end if row_index != -1
            
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
    
    
    
    
    
    private class TableSelectionListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row_index = table.getSelectedRow();
            
            if(article == null){
                if(!table.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(null, "No Article from List is Selected", "Error" ,JOptionPane.WARNING_MESSAGE);
                    table.clearSelection();
                    
                    table.getCellEditor(row_index, 1).cancelCellEditing();
                    table.getCellEditor(row_index, 2).cancelCellEditing();
                    table.getCellEditor(row_index, 3).cancelCellEditing();
                }
            } else{
              if(row_index !=-1){
                if(table.getValueAt(row_index, 1)==null){
                    //type has not been chosen
                    txt_tab_date.setEnabled(false);
                    txt_tab_qte.setEnabled(false);
                    combo_clients.setEnabled(false);
                    combo_clients.setEditable(false);
                    combo_fourn.setEnabled(false);
                    combo_fourn.setEditable(false);
                    col_2.getCellEditor().stopCellEditing();
                    col_3.getCellEditor().stopCellEditing();
                    col_4.getCellEditor().stopCellEditing();
                    
                    combo_type.setEnabled(true);
                    combo_type.setEditable(true);
                    
                } else {
                    //type has been chosen
                    combo_type.setEnabled(false);
                    combo_type.setEditable(false);
                    col_1.getCellEditor().stopCellEditing();
                    table.getCellEditor(row_index, 1).cancelCellEditing();
                    
                    if(table.getValueAt(row_index, 2)==null){
                        client = null;
                        fourn = null;
                        
                        txt_tab_date.setEnabled(false);
                        txt_tab_qte.setEnabled(false);
                        col_3.getCellEditor().stopCellEditing();
                        col_4.getCellEditor().stopCellEditing();
                        
                        combo_clients.setEnabled(true);
                        combo_clients.setEditable(true);
                        combo_fourn.setEnabled(true);
                        combo_fourn.setEditable(true);
                    } else {
                        txt_tab_date.setEnabled(true);
                        txt_tab_qte.setEnabled(true);
                        combo_clients.setEnabled(true);
                        combo_clients.setEditable(true);
                        combo_fourn.setEnabled(true);
                        combo_fourn.setEditable(true);
                    }
                    
                    
                    if(table.getValueAt(row_index, 0).toString()!= null){
                    int key = Integer.parseInt(table.getValueAt(row_index, 0).toString());
                    String detail_key = key+","+article.getNoArticle();
                  
                    String desc = table.getValueAt(row_index, 1).toString();
                    
                    if(desc.equals(Transaction.Description.Vente)){
                        col_2.setCellEditor(new DefaultCellEditor(combo_clients));
                        col_2.getCellEditor().addCellEditorListener(new ComboCliFournListener());
                        
                        if(table.getValueAt(row_index, 2)!=null){
                            client = (Client) table.getValueAt(row_index, 2);
                            fourn = null;
                        }
                        
                        //details vente (old)
                        detail_vente = Globals.map_detailsVente.get(detail_key);
                        vente = Globals.map_ventes.get(key);
                        detail_achat = null;
                        achat = null;
                        
                    }

                    if(desc.equals(Transaction.Description.Achat)){
                        col_2.setCellEditor(new DefaultCellEditor(combo_fourn));
                        col_2.getCellEditor().addCellEditorListener(new ComboCliFournListener());
                        
                        if(table.getValueAt(row_index, 2)!=null){
                            fourn = (Fournisseur) table.getValueAt(row_index, 2);
                            client = null;
                        }
                        
                        //detail achat (old)
                        detail_achat = Globals.map_detailsAchat.get(detail_key);
                        achat = Globals.map_achats.get(key);
                        detail_vente = null;
                        vente = null;
                        
                    }
                    }

                }//if type != null
                  
                
              }//end if row num != -1
              
            }//end if article != null
           
        }
        
    }
    
    
    
    
        
    private void RefreshTable(){
        tableModel = MyFilter.FilterDetailsByArticle(tableModel,article);
        table.setModel(tableModel);
    }
    
    
    
    
        
    private class CatArtComboListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            lbl_error.setText("");
            list_articles.setSelectedIndex(-1);
        
            int nb_cat_art = combo_cat_art.getSelectedIndex();
            
            //if new don't disable the txt field
            String nb_serial_v = Article.getNoSerie()+"";
            if(!txt_no.getText().equals(nb_serial_v)){ 
                txt_nom.setEnabled(false);
                txt_profit.setEnabled(false);
                txt_cout.setEnabled(false);
            
            }
            
            
            if(nb_cat_art!=0){ 
                cat_art =(CategorieArticle) combo_cat_art.getSelectedItem();
            }
            else { article = null; }
            
            
            RefreshList();
            RefreshTable();
        }
    }
    
    
    
    
    
    public void RefreshList(){
        mod_articles = MyFilter.FilterArticlesByCategorie(cat_art);
        list_articles.setModel(mod_articles);
        list_articles.revalidate();
    }
    
    
    
    
       
    private class ListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            lbl_error.setText("");
            //set values of article
            if(list_articles.getSelectedIndex() != -1){
                article = (Article) list_articles.getSelectedValue();
                
                txt_no.setText(""+article.getNoArticle());
                
                txt_nom.setText(""+article.getNomArticle());
                txt_nom.setBackground(Color.white);
                txt_nom.setEnabled(true);
                
                txt_cout.setBackground(Color.white);
                txt_cout.setEnabled(true);
                txt_cout.setText(""+article.getCoutAchatParUnite());
                
                txt_profit.setBackground(Color.white);
                txt_profit.setEnabled(true);
                txt_profit.setText(""+article.getTauxProfit());
                
                txt_qte.setText(""+article.getQteStock());
                txt_prix.setText(""+article.getPrixVenteParUnite());

                
                RefreshTable();
                
            } else { article = null; }
        }
    }
    
    
    
        
    
    private class BtnSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(cat_art == null){
                //Error Handling : A categorie must be selected in order to continue
                JOptionPane.showMessageDialog(null, "No Category selected.", "Error" ,JOptionPane.WARNING_MESSAGE);
                combo_cat_art.requestFocusInWindow();
            }
            else {
                
                try{
                    //get the profit && cout chosen
                    Double profit = Double.parseDouble(txt_profit.getText());
                    Double cout = Double.parseDouble(txt_cout.getText());
                    
                    if(txt_nom.getText().equals("")){
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Please fill in all fields.");
                    }else {
                    
                    String currentnb = Article.getNoSerie()+"";
                    if(currentnb.equals(txt_no.getText())){
                        //if new article
                        article = new Article(txt_nom.getText(), profit);
                        article.setCoutAchatParUnite(cout);
                        article.setCategorieArticle(cat_art);
                        Globals.map_articles.put(article.getNoArticle(),article);
                        Globals.setSerialNbs();
                        
                        for(int i=0; i<mod_articles.getSize(); i++){
                            if(mod_articles.getElementAt(i).toString().equals(article.toString())){
                                list_articles.setSelectedIndex(i);
                                article = (Article) list_articles.getSelectedValue();
                                break;
                            }
                        }
                    
                        
                    }
                    else {
                        //if old article
                        article.setCoutAchatParUnite(cout);
                        article.setCategorieArticle((CategorieArticle)combo_cat_art.getSelectedItem());
                        article.setNom(txt_nom.getText());
                        article.setProfit(profit);
                        Globals.map_articles.put(article.getNoArticle(), article);
                    }
                    
                    //sort again
                    MyFunctions.AddSortedDataArticles();
                    Globals.getFileArticles();
                    RefreshList();
                    
                    //show success message
                    lbl_error.setForeground(Color.GREEN);
                    lbl_error.setText("The data was successfully saved.");
                
                    }   
                }
                catch(NullPointerException ex3){
                    lbl_error.setForeground(Color.red);
                    lbl_error.setText("Choose an article first!");
                }
                catch(NumberFormatException ex44){
                    lbl_error.setForeground(Color.red);
                    lbl_error.setText("Profit and Cout must be numbers.");
                }
            }
        }
    }
    
    
    
    
    
    private class BtnCreateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_error.setText("");
            
            String noSerie = ""+Article.getNoSerie();
            txt_no.setText(noSerie);
            txt_nom.setText("");
            txt_nom.setBackground(Color.WHITE);
            txt_nom.setEnabled(true);
            txt_cout.setText("0");
            txt_cout.setBackground(Color.WHITE);
            txt_cout.setEnabled(true);
            txt_profit.setText("0");
            txt_profit.setBackground(Color.white);
            txt_profit.setEnabled(true);
            
            txt_prix.setText("0");
            txt_qte.setText("0");

            
            txt_nom.requestFocusInWindow();
            list_articles.setSelectedIndex(-1);
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
    
    
    
    
    
}
