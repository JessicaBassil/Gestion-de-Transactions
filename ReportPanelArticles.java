package gestiondetransactionscommerciales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.CENTER;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class ReportPanelArticles extends JPanel{

    private JPanel pan_north, pan_south, pan_filter, pan_quitter;
    private JButton btn_ok, btn_quitter;
    private String str_title;
    private String [] a_tab_headers;
    private DataMenu data_menu;
    private TitledBorder border;
    
    private JLabel lbl_from, lbl_to, lbl_categories, lbl_articles, lbl_vente, lbl_achat;
    private JTextField txt_from, txt_to;
    private JComboBox<CategorieArticle> combo_categories;
    private JComboBox<Article> combo_articles;
    private JCheckBox box_vente, box_achat;
    
    private JLabel lbl_error;

    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scroll;
    
    private LocalDate date_from, date_to;
    
    
    
    
    
    public ReportPanelArticles(DataMenu data_menu){
        Globals.getFileArticles();
        Globals.getFileCategorieArticle();
        Globals.getFileVentes();
        Globals.getFileAchats();
        Globals.getFileDetailAchat();
        Globals.getFileDetailVente();

        //assignments
        pan_north = new JPanel();
        pan_south = new JPanel();
        pan_filter = new JPanel();
        pan_quitter = new JPanel();
        
        btn_ok = new JButton("OK");
        btn_quitter = new JButton("Quitter");
        
        lbl_from = new JLabel("De Date", SwingConstants.CENTER);
        lbl_to = new JLabel("A Date", SwingConstants.CENTER);
        lbl_categories = MyFunctions.ChangeWidth("Categorie Article");
        lbl_articles = MyFunctions.ChangeWidth("Article");
        lbl_vente = new JLabel("Vente", SwingConstants.CENTER);
        lbl_achat = new JLabel("Achat", SwingConstants.CENTER);
        
        box_vente = new JCheckBox();
        box_achat = new JCheckBox();
        
        txt_from = new JTextField(5);
        txt_to = new JTextField(5);
        
        lbl_error = new JLabel();
        combo_categories = new JComboBox();
        combo_articles = new JComboBox();
        
        //add values to combo box
        combo_categories = MyFunctions.AddDataToComboBox(Globals.map_categoriesArticles);
        combo_articles = MyFunctions.AddDataToComboBox(Globals.map_articles);
        
                
        //Data assignments
        this.data_menu = data_menu;
        str_title = "Rapports Articles";
        border = BorderFactory.createTitledBorder(null, str_title, CENTER, 0);
        
        a_tab_headers = new String[]{"No Trs","Description","Date Trs","Categorie","Article", "Quantite", "Prix/Cout", "Total"};
        tableModel = new DefaultTableModel(a_tab_headers,40){
                @Override
                public boolean isCellEditable(int row, int col) {
                     switch (col) {
                         default:
                             return false;
                      }
                }
            };
        
        table = new JTable(tableModel);
        scroll = new JScrollPane(table);

        
        //GUI design
        this.setBorder(border);
        this.setLayout(new BorderLayout());
        this.add(pan_north, BorderLayout.NORTH);
        this.add(pan_south, BorderLayout.CENTER);
        
        pan_north.setLayout(new BorderLayout());
        pan_north.add(pan_filter, BorderLayout.WEST);
        pan_north.add(pan_quitter, BorderLayout.EAST);
        
        pan_south.setLayout(new BorderLayout());
        pan_south.add(scroll, BorderLayout.CENTER);
        
        pan_quitter.setLayout(new BorderLayout());
        pan_quitter.add(btn_quitter, BorderLayout.NORTH);
        
        pan_filter.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,5,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridwidth =1;
        gbc.gridy = 0;
        
        gbc.gridx = 0;
        pan_filter.add(lbl_from, gbc);
        
        gbc.gridx = 1;
        pan_filter.add(lbl_to, gbc);
        
        gbc.gridy = 1;
        
        gbc.gridx = 0;
        pan_filter.add(txt_from, gbc);
        
        gbc.gridx = 1;
        pan_filter.add(txt_to, gbc);
        
        gbc.gridx = 8;
        pan_filter.add(btn_ok, gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 2;
        
        gbc.gridy = 0;
        pan_filter.add(lbl_categories, gbc);
        
        gbc.gridy = 1;
        pan_filter.add(combo_categories,gbc);
        
        gbc.gridx = 4;
        
        gbc.gridy = 0;
        pan_filter.add(lbl_articles,gbc);
        
        gbc.gridy = 1;
        pan_filter.add(combo_articles,gbc);
        
        gbc.gridwidth =1;
        
        gbc.gridx = 6;
        
        gbc.gridy = 0;
        pan_filter.add(lbl_vente, gbc);
        
        gbc.gridy = 1;
        pan_filter.add(box_vente, gbc);
        
        gbc.gridx = 7;
        
        gbc.gridy = 0;
        pan_filter.add(lbl_achat, gbc);
        
        gbc.gridy = 1;
        pan_filter.add(box_achat, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        pan_filter.add(lbl_error, gbc);
        
        //add ActionListeners
        btn_quitter.addActionListener(new BtnQuitListener());
        txt_from.getDocument().addDocumentListener(new DateTextListener());
        txt_to.getDocument().addDocumentListener(new DateTextListener());
        btn_ok.addActionListener(new BtnOKListener());
                
        //check all check boxes at start
        box_vente.setSelected(true);
        box_achat.setSelected(true);

        //add all data on start
        RefreshTable();
    }





    private class BtnOKListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RefreshTable();
        }
    }
    
    
    
    
    
    private void RefreshTable(){
        try{
        CategorieArticle categorie =null;
        if(!combo_categories.getSelectedItem().equals("--Select--")){
            categorie = (CategorieArticle)combo_categories.getSelectedItem(); 
        }
            
        Article article = null;
        if(!combo_articles.getSelectedItem().equals("--Select--")){
            article = (Article)combo_articles.getSelectedItem();
        }
    
        boolean isVente = false, isAchat = false;
    
        if(box_vente.isSelected()){ isVente = true; }
        if(box_achat.isSelected()) { isAchat = true;}
        
        tableModel.setRowCount(0);
           
        
        
        if(!Globals.map_detailsAchat.isEmpty() && isAchat){
            Iterator it = Globals.map_detailsAchat.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Integer, DetailAchat> pair = (Map.Entry<Integer, DetailAchat>) it.next();
                CategorieArticle pair_categorie = pair.getValue().getArticle().getCategorieArticle();
                Article pair_article = pair.getValue().getArticle();
                
                if((categorie == null || pair_categorie.getNoCategorie() == categorie.getNoCategorie())
                        && (article == null || pair_article.getNoArticle() == article.getNoArticle())
                        && (date_from==null || date_from.compareTo(pair.getValue().getAchat().getDateTrans())<0)
                        && (date_to == null || date_to.compareTo(pair.getValue().getAchat().getDateTrans())>0) ){
                    
                    
                        Object[]data ={
                            pair.getValue().getAchat().getNoTrans()+"",
                            pair.getValue().getAchat().getDesc()+"",
                            pair.getValue().getAchat().getDateTrans()+"",
                            pair.getValue().getArticle().getCategorieArticle().toString()+"",
                            pair.getValue().getArticle().toString()+"",
                            pair.getValue().getQuantite()+"",
                            pair.getValue().getCout()+"",
                            pair.getValue().getCout()*pair.getValue().getQuantite()+""
                        };
                        tableModel.addRow(data);
                }
            }//end while
            
        }//end if map recus not empty
                    
            

        
        if(!Globals.map_detailsVente.isEmpty() && isVente){
            Iterator it = Globals.map_detailsVente.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Integer, DetailVente> pair = (Map.Entry<Integer, DetailVente>) it.next();
                CategorieArticle pair_categorie = pair.getValue().getArticle().getCategorieArticle();
                Article pair_article = pair.getValue().getArticle();
                
                if((categorie == null || pair_categorie.getNoCategorie() == categorie.getNoCategorie())
                        && (article == null || pair_article.getNoArticle() == article.getNoArticle())
                        && (date_from==null || date_from.compareTo(pair.getValue().getVente().getDateTrans())<0)
                        && (date_to == null || date_to.compareTo(pair.getValue().getVente().getDateTrans())>0) ){
                    
                    
                        Object[]data ={
                            pair.getValue().getVente().getNoTrans()+"",
                            pair.getValue().getVente().getDesc()+"",
                            pair.getValue().getVente().getDateTrans()+"",
                            pair.getValue().getArticle().getCategorieArticle().toString()+"",
                            pair.getValue().getArticle().toString()+"",
                            pair.getValue().getQuantite()+"",
                            pair.getValue().getPrixVente()+"",
                            pair.getValue().getPrixVente()*pair.getValue().getQuantite()+""
                        };
                        tableModel.addRow(data);
                    
                }
            }//end while
            
        }//end if map ventes not empty
        
        if(tableModel.getRowCount()<40){
            int add = 40 - tableModel.getRowCount();
            tableModel.setRowCount(tableModel.getRowCount()+add);
        }
        
        table.setModel(tableModel);
        }
        catch(NullPointerException e){}
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
                String str_from = ""+txt_from.getText();
                String str_to = ""+txt_to.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date_from = null;
                date_to = null;
                if(!"".equals(str_from)){ date_from = LocalDate.parse(str_from, formatter);} else {date_from = null;}
                if(!"".equals(str_to)) {date_to = LocalDate.parse(str_to, formatter);} else {date_to = null;}
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
    
    
    
    
    
}
