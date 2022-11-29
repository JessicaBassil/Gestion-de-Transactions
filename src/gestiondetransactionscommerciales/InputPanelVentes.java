package gestiondetransactionscommerciales;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.CENTER;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



public class InputPanelVentes extends JPanel{
    

    private JPanel pan_north,pan_south, pan_left, pan_right, pan_in_data, pan_combo, pan_list, pan_in_cont ;
    private JButton btn_creer, btn_enregistrer, btn_quitter;
    private String str_title;
    private String [] a_tab_headers;
    private DataMenu data_menu;
    private TitledBorder border;
    private JLabel lbl_no, lbl_desc, lbl_date, lbl_montant, lbl_clients, lbl_table;
    private JTextField txt_no, txt_desc, txt_date, txt_montant;
    private JLabel lbl_error;
    private JTextField txt_qte;
    
    private JComboBox<Client> combo_clients;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scroll;
    private TableColumn col_first, col_second;
    private JComboBox combo_article;
    
    private Client client;
    private Vente vente;
    
    
    private JList<Vente> list_ventes;
    private DefaultListModel mod_ventes;
    
    private Article article;
    private DetailVente detailvente;
    private double totalPrice = 0.0;
    private double montant = 0.0;
    
    private int quantite = 0;
    private int error_row_nb = -1;
    
    
    
    
    
    public InputPanelVentes(DataMenu data_menu) {
        this.revalidate();
        this.repaint();
        Globals.getFileClients();
        Globals.getFileDetailVente();
        Globals.getFileVentes();
        Globals.getFileArticles();

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
        
        lbl_no = new JLabel("No Vente", SwingConstants.RIGHT);
        lbl_desc = new JLabel("Description", SwingConstants.RIGHT);
        lbl_date = new JLabel("Date Vente", SwingConstants.RIGHT);
        lbl_montant = new JLabel("Montant Vente", SwingConstants.RIGHT);
        lbl_clients = new JLabel("Choisir Client", SwingConstants.CENTER);
        lbl_table = new JLabel("Details Vente");
        
        txt_no = new JTextField(19);
        txt_desc = new JTextField(19);
        txt_date = new JTextField(19);
        txt_montant = new JTextField(19);
        txt_qte = new JTextField();
        
        Border txt_border = txt_no.getBorder();
        Border actual_txt_border = BorderFactory.createCompoundBorder(txt_border, BorderFactory.createEmptyBorder(2, 0, 2, 0));
        txt_no.setBorder(actual_txt_border);
        txt_desc.setBorder(actual_txt_border);
        txt_date.setBorder(actual_txt_border);
        txt_montant.setBorder(actual_txt_border);
        
        lbl_error = new JLabel();
        
        combo_clients = new JComboBox();
        combo_article = new JComboBox();
        list_ventes = new JList();
        mod_ventes = new DefaultListModel();
        
        
        //Data assignments
        this.data_menu = data_menu;
        str_title = "Ventes";
        border = BorderFactory.createTitledBorder(null, "Infos "+str_title, CENTER, 0);
        
        a_tab_headers = new String[]{"Article","Quantite","Prix Unit","Prix Total"};
        tableModel = new DefaultTableModel(a_tab_headers,20){
                @Override
                public boolean isCellEditable(int row, int col) {
                     switch (col) {
                         case 0:
                         case 1:
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
        combo_article = MyFunctions.AddDataToComboBox(Globals.map_articles);
        
        


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
        pan_in_data.add(lbl_desc,gbc);
        
        gbc.gridy = 3;
        pan_in_data.add(lbl_date,gbc);
        
        gbc.gridy = 4;
        pan_in_data.add(lbl_montant,gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        
        gbc.gridy = 1;
        pan_in_data.add(txt_no,gbc);
        
        gbc.gridy = 2;
        pan_in_data.add(txt_desc,gbc);
        
        gbc.gridy = 3;
        pan_in_data.add(txt_date,gbc);
        
        gbc.gridy = 4;
        pan_in_data.add(txt_montant,gbc);
        
        pan_combo.setLayout(new GridLayout(1,2,10,10));
        pan_combo.add(lbl_clients);
        pan_combo.add(combo_clients);
        
        pan_list.setBorder(border);
        pan_list.setBackground(Color.white);
        pan_list.add(list_ventes);
        
        
        Color col = Color.getHSBColor(0.575F,0.03F,0.97F);
        txt_no.setBackground(col);
        txt_desc.setBackground(col);
        txt_date.setBackground(col);
        txt_montant.setBackground(col);
        
        lbl_error.setForeground(Color.RED);
        
        
        //disable all input components on start
        txt_no.setEnabled(false);
        txt_desc.setEnabled(false);
        txt_date.setEnabled(false);
        txt_montant.setEnabled(false);
        
        
        //add ActionListeners
        table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
        combo_clients.addActionListener(new ClientComboListener());
        
        btn_creer.addActionListener(new BtnCreateListener());
        btn_enregistrer.addActionListener(new BtnSaveListener());
        btn_quitter.addActionListener(new BtnQuitListener());
        
        list_ventes.addListSelectionListener(new ListListener());
        txt_date.getDocument().addDocumentListener(new DateTextListener());
        combo_article.addActionListener(new ArticleComboListener());
        txt_qte.addActionListener(new QuantityTextListener());

        //table
        col_first = table.getColumnModel().getColumn(0);
        col_second = table.getColumnModel().getColumn(1);
        col_first.setCellEditor(new DefaultCellEditor(combo_article));
        col_second.setCellEditor(new DefaultCellEditor(txt_qte));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    
    
    
    
    private class QuantityTextListener implements ActionListener{

        public void updateQuantity(){
            int row_index = table.getSelectedRow();
            
            String str_qte = table.getValueAt(row_index, 1).toString();
            try{
                int old_qte = quantite;
                quantite = Integer.parseInt(str_qte);
                if(article!=null){
                    if(!article.sortieStock(quantite)){   
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Quantity is greater than available quantity in stock :"+article.getQteStock());
                        table.setValueAt(0, row_index, 1);
                    } else {
                        article.entreeStock(old_qte, article.getCoutAchatParUnite());
                        table.setValueAt(article.getPrixVenteParUnite(), row_index, 2);
                        detailvente.setQuantite(quantite);
                        
                        Globals.map_articles.get(article.getNoArticle()).entreeStock(old_qte, article.getCoutAchatParUnite());
                        Globals.map_articles.get(article.getNoArticle()).sortieStock(quantite);
                        
                        // change montant of selected vente too
                        double old_montant = montant;
                        montant = detailvente.calculerMontant();
                        
                        tableModel.setValueAt(montant, row_index, 3);
                        table.setModel(tableModel);
                        table.revalidate();
                        totalPrice += montant - old_montant;
                        vente.UndoMiseAJourCompte(old_montant);
                        vente.miseAJourCompte(totalPrice);
                        txt_montant.setText(""+totalPrice);
                        
                        Globals.map_detailsVente.get(vente.getNoTrans()+","+article.getNoArticle()).setQuantite(quantite);
                        MyFunctions.AddData(Globals.FileName.DetailVente, Globals.map_detailsVente);
                        
                        Globals.map_ventes.put(vente.getNoTrans(), vente);
                        MyFunctions.AddData(Globals.FileName.Ventes, Globals.map_ventes);
                        error_row_nb = -1;
                    }
                } else {
                    error_row_nb = row_index;
                    JOptionPane.showMessageDialog(null, "No Article Selected", "Error" ,JOptionPane.WARNING_MESSAGE);
                    table.getCellEditor(row_index, 1).cancelCellEditing();
                }
            }
            catch(NumberFormatException exc){
                error_row_nb = row_index;
                lbl_error.setForeground(Color.red);
                lbl_error.setText("Quantity must be an Integer.");
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.updateQuantity();
        }
    }
    
    
    
    
    
    private class TableSelectionListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row_index = table.getSelectedRow();
            
            
            
            if(error_row_nb != -1 && error_row_nb != row_index){
                //if the previous editing row has errors
                JOptionPane.showMessageDialog(null, "Please Fix errors before proceeding.", "Error not handled.", JOptionPane.WARNING_MESSAGE);
                table.setRowSelectionInterval(error_row_nb, error_row_nb);
            }
            else {
            
            if(client == null || vente == null ){
                if(!table.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(null, "No Vente from List is Selected", "Error" ,JOptionPane.WARNING_MESSAGE);
                    table.clearSelection();
                    table.getCellEditor(row_index, 0).cancelCellEditing();
                }
            } else{
              if(table.getValueAt(row_index, 0)!=null){ article = (Article)table.getValueAt(row_index, 0); } 
              else {article = null;}
              if(article!=null){
                detailvente = Globals.map_detailsVente.get(vente.getNoTrans()+","+article.getNoArticle());
              } else {detailvente = null;}
              if(table.getValueAt(row_index, 1)!=null){
                  if(article!=null){
                        String str_qte = table.getValueAt(row_index, 1).toString();
                        String str_montant = table.getValueAt(row_index, 3).toString();
                        try{
                            quantite = Integer.parseInt(str_qte);
                            if(str_montant!=null){
                                montant = Double.parseDouble(str_montant);
                            }
                        }
                        catch(NumberFormatException exc){}
                    }
                  else {
                    JOptionPane.showMessageDialog(null, "No Article Selected", "Error" ,JOptionPane.WARNING_MESSAGE);
                  }
                  
               } else { quantite = 0;}
        
              }
           
        }
            
        }
        
    }
    
    
    
    
    
    private class ArticleComboListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Article> cb = (JComboBox)e.getSource();
            
            //0 is for null value
            if(cb.getSelectedIndex()>0 && client !=null && vente != null){

                Article article_new = (Article)cb.getSelectedItem();
                String key = vente.getNoTrans()+","+article_new.getNoArticle();
                String old_key = "";
                if(article!=null){ old_key = vente.getNoTrans()+","+article.getNoArticle();}

                int row_index = table.getSelectedRow();

                if(Globals.map_detailsVente.containsKey(key)){
                    if(article!=null){ table.setValueAt(article, row_index, 0);}
                    else {table.setValueAt(cb.getItemAt(0), row_index, 0);}
                    JOptionPane.showMessageDialog(null, "This Article has already been added!", "Error" ,JOptionPane.WARNING_MESSAGE);
                } else {
                    if(article != null) {Globals.map_detailsVente.remove(old_key);}
                    if(article_new!=null){
                        detailvente = new DetailVente(vente, article_new, 0);
                        Globals.map_detailsVente.put(key, detailvente);
                        MyFunctions.AddData(Globals.FileName.DetailVente, Globals.map_detailsVente);
                        table.setValueAt(article_new.getPrixVenteParUnite(), row_index, 2);
                    }
                    article = article_new;
                }
            } else {
                //delete the row from table if the null value is selected
                if(article!=null && vente!=null) Globals.map_detailsVente.remove(vente.getNoTrans()+","+article.getNoArticle());
            }
        }
    
    }
    
    
    
    
    
    private void RefreshTable(){
        tableModel = MyFilter.FilterDetailVenteByVente(tableModel,vente);
        table.setModel(tableModel);
    }
    
    
    
    
    
    private class ClientComboListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            lbl_error.setText("");
            list_ventes.setSelectedIndex(-1);
        
            int nb_client = combo_clients.getSelectedIndex();
            
            //if new don't disable the txt field
            String nb_serial_v = Transaction.getNoSerie()+"";
            if(!txt_no.getText().equals(nb_serial_v)){ txt_date.setEnabled(false); }
            
            
            if(nb_client!=0){ client =(Client) combo_clients.getSelectedItem(); }
            else { client = null; }
            
            RefreshList();
            RefreshTable();
        }
    }
    
    
    
    
    
    public void RefreshList(){
        mod_ventes = MyFilter.FilterVentesByClient(client);
        list_ventes.setModel(mod_ventes);
        list_ventes.revalidate();
    }
    
    
    
    
    
    private class BtnSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(client == null){
                //Error Handling : A client must be selected in order to continue
                JOptionPane.showMessageDialog(null, "No Client selected.", "Error" ,JOptionPane.WARNING_MESSAGE);
                combo_clients.requestFocusInWindow();
            } 
            else {
                
                try{
                    //get the date chosen
                    String str_date = txt_date.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(str_date, formatter);
                    
                    String currentnb = Transaction.getNoSerie()+"";
                    if(currentnb.equals(txt_no.getText())){
                        //if new vente
                        vente = new Vente(date, Double.parseDouble(txt_montant.getText()), client);
                        Globals.map_ventes.put(vente.getNoTrans(),vente);
                        Globals.setSerialNbs();
                        
                        for(int i=0; i<mod_ventes.getSize(); i++){
                            if(mod_ventes.getElementAt(i).toString().equals(vente.toString())){
                                list_ventes.setSelectedIndex(i);
                                vente = (Vente) list_ventes.getSelectedValue();
                                break;
                            }
                        }
                    
                        
                    }
                    else {
                        //if old vente
                        Globals.map_ventes.get(vente.getNoTrans()).miseAJourCompte(Double.parseDouble(txt_montant.getText()));
                        Globals.map_ventes.get(vente.getNoTrans()).setDateTrans(date);
                    }
                    
                    //sort again
                    MyFunctions.AddSortedDataVentes();
                    Globals.getFileVentes();
                    RefreshList();
                    
                    //show success message
                    lbl_error.setForeground(Color.GREEN);
                    lbl_error.setText("The data was successfully saved.");
                    
                }catch(DateTimeParseException ex){
                    lbl_error.setForeground(Color.red);
                    lbl_error.setText("Dates must have this format : yyyy-MM-dd");   
                }
                catch(NullPointerException ex3){
                    lbl_error.setForeground(Color.red);
                    lbl_error.setText("Choose a vente first!");
                }
                catch(Exception ex2){
                    System.out.println("Error:"+ex2);    
                }
            }
        }
    }
    
    
    
    
    
    private class ListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            lbl_error.setText("");
            //set values of vente
            if(list_ventes.getSelectedIndex() != -1){
                vente = (Vente) list_ventes.getSelectedValue();
                txt_no.setText(""+vente.getNoTrans());
                txt_desc.setText(""+vente.getDesc());
                txt_date.setText(""+vente.getDateTrans());
                txt_montant.setText(""+vente.getMontant());
                txt_date.setEnabled(true);
                txt_date.setBackground(Color.white);
                RefreshTable();
                
            } else { vente = null; }
        }
    }
    
    
    
    
    
    private class DateTextListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            CheckDateError();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            CheckDateError();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            CheckDateError();
        }
        
        public void CheckDateError(){
            try{
                String str_date = txt_date.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(str_date, formatter);
                lbl_error.setText("");
            }
            catch(DateTimeParseException e){
                lbl_error.setForeground(Color.red);
                lbl_error.setText("Dates must have this format : yyyy-MM-dd");
            }
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
            
            String noSerie = ""+Transaction.getNoSerie();
            txt_no.setText(noSerie);
            txt_desc.setText(Transaction.Description.Vente.toString());
            txt_date.setEnabled(true);
            txt_date.setBackground(Color.WHITE);
            txt_date.setText("");
            txt_montant.setText("0.00");
            txt_date.requestFocusInWindow();
            list_ventes.setSelectedIndex(-1);
        }
    }
    
    
    
    
    
}

