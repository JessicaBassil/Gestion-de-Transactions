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


public class InputPanelCategoriesArticles extends JPanel{

    private JPanel pan_north, pan_south, pan_left, pan_right, pan_in_data, pan_list, pan_in_cont;
    private JButton btn_creer, btn_enregistrer, btn_quitter;
    private String str_title;
    private String [] a_tab_headers;
    private DataMenu data_menu;
    private TitledBorder border;
    
    private JLabel lbl_no, lbl_nom,lbl_table;
    private JTextField txt_no, txt_nom;
    
    private JLabel lbl_error;

    private JTextField txt_tab_nom, txt_tab_cout, txt_tab_profit;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scroll;
    private TableColumn col_1, col_4, col_5;
    
    private JList<CategorieArticle> list_categories;
    private DefaultListModel mod_categories;
    
    private CategorieArticle categorie;
    private Article article;
    
    
    
    
    
    public InputPanelCategoriesArticles(DataMenu data_menu) {
        Globals.getFileCategorieArticle();
        Globals.getFileArticles();
        
        
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

                
        lbl_no = new JLabel("No Categorie", SwingConstants.RIGHT);
        lbl_nom = new JLabel("Nom Categorie", SwingConstants.RIGHT);
        
        lbl_table = new JLabel("Articles");
        
        txt_no = new JTextField(19);
        txt_nom = new JTextField(19);
        
        Border txt_border = txt_no.getBorder();
        Border actual_txt_border = BorderFactory.createCompoundBorder(txt_border, BorderFactory.createEmptyBorder(2, 0, 2, 0));
        txt_no.setBorder(actual_txt_border);
        txt_nom.setBorder(actual_txt_border);
        
        txt_tab_nom = new JTextField();
        txt_tab_cout = new JTextField();
        txt_tab_profit = new JTextField();
                
        lbl_error = new JLabel();
        
        list_categories = new JList();
        mod_categories = new DefaultListModel();
        
        
        //Data assignments
        this.data_menu = data_menu;
        str_title = "Categories Articles";
        border = BorderFactory.createTitledBorder(null,str_title, CENTER, 0);
        
        a_tab_headers = new String[]{"No Article","Nom Article","Qte Stock","Prix Vente", "Cout Achat", "Profit"};
        tableModel = new DefaultTableModel(a_tab_headers,20){
                @Override
                public boolean isCellEditable(int row, int col) {
                     switch (col) {
                         case 1:
                         case 4:
                         case 5:
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
        pan_list.add(list_categories);
        
        
        Color col = Color.getHSBColor(0.575F,0.03F,0.97F);
        txt_no.setBackground(col);
        txt_nom.setBackground(col);
        
        lbl_error.setForeground(Color.RED);
        
        
        //disable all input components on start
        txt_no.setEnabled(false);
        txt_nom.setEnabled(false);
        
        //table
        col_1 = table.getColumnModel().getColumn(1);
        col_4 = table.getColumnModel().getColumn(4);
        col_5 = table.getColumnModel().getColumn(5);
        
        col_1.setCellEditor(new DefaultCellEditor(txt_tab_nom));
        col_4.setCellEditor(new DefaultCellEditor(txt_tab_cout));
        col_5.setCellEditor(new DefaultCellEditor(txt_tab_profit));
       
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
        //get categories list
        if(!Globals.map_categoriesArticles.isEmpty()){
            int i=0;
            Iterator it = Globals.map_categoriesArticles.entrySet().iterator();
            
            while(it.hasNext()){
                Map.Entry<Integer,CategorieArticle> pair = (Map.Entry<Integer,CategorieArticle>)it.next();
                mod_categories.add(i,pair.getValue());
                list_categories.setModel(mod_categories);
                i++;
            }

        }
        
        
        
        //add ActionListeners
        
          table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
        
          list_categories.addListSelectionListener(new ListListener());
          col_1.getCellEditor().addCellEditorListener(new TabTextNomListener());
          col_4.getCellEditor().addCellEditorListener(new TabTextCoutListener());
          col_5.getCellEditor().addCellEditorListener(new TabTextProfitListener());
        
          btn_creer.addActionListener(new BtnCreateListener());
          btn_enregistrer.addActionListener(new BtnSaveListener());
          btn_quitter.addActionListener(new BtnQuitListener());
    }
    
    
    
    
    
    private class TabTextProfitListener implements CellEditorListener{

        public void CheckTextChanged(){
            
            int row_index = table.getSelectedRow();
            if(row_index!=-1){
                if(table.getValueAt(row_index, 0)!= null){
                    //old article
                    int no_article = Integer.parseInt(table.getValueAt(row_index,0).toString());
                    article = Globals.map_articles.get(no_article);
                    if(table.getValueAt(row_index, 5)== null){
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Article Profit can not be null.");
                        table.setValueAt(article.getTauxProfit(), row_index, 5);
                    }else{
                        try{
                            double profit = Double.parseDouble(table.getValueAt(row_index, 5).toString());
                            article.setProfit(profit);
                            article.setPrixVenteParUnite();
                            table.setValueAt(article.getPrixVenteParUnite(), row_index, 3);
                            Globals.map_articles.put(article.getNoArticle(), article);
                            MyFunctions.AddSortedDataArticles();
                            lbl_error.setText("");
                        }catch(NumberFormatException eee){
                            lbl_error.setForeground(Color.red);
                            lbl_error.setText("Profit must be a Double.");
                        }
                    }
                } else {
                    //new article
                    if(table.getValueAt(row_index, 5)== null){
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Article Profit can not be null.");
                    } else {
                        try{
                            double profit = Double.parseDouble(table.getValueAt(row_index, 5).toString());
                            String str_art_nom = "Article Name";
                            table.setValueAt(str_art_nom, row_index, 1);
                            table.setValueAt(0, row_index, 2);
                            table.setValueAt(0.0, row_index, 4);
                            
                            article = new Article(str_art_nom, profit);
                            table.setValueAt(article.getNoArticle(), row_index, 0);
                            
                            article.setPrixVenteParUnite();
                            table.setValueAt(article.getPrixVenteParUnite(), row_index, 3);
                            
                            article.setCategorieArticle(categorie);
                            Globals.map_articles.put(article.getNoArticle(), article);
                            MyFunctions.AddSortedDataArticles();
                            lbl_error.setText("");
                        }
                        catch(NumberFormatException eee){
                            lbl_error.setForeground(Color.red);
                            lbl_error.setText("Profit must be a Double.");
                        }
                        
                    }//end if cout not null
                    
                }// end if new article
                
            }//end if row_index != -1
        
        }
        
        @Override
        public void editingStopped(ChangeEvent e) {
            CheckTextChanged();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            CheckTextChanged();
        }
    
    }
    
    
    
    
    
    private class TabTextCoutListener implements CellEditorListener{

        public void CheckTextChanged(){
        
            int row_index = table.getSelectedRow();
            if(row_index!=-1){
                if(table.getValueAt(row_index, 0)!= null){
                    //old article
                    int no_article = Integer.parseInt(table.getValueAt(row_index,0).toString());
                    article = Globals.map_articles.get(no_article);
                    if(table.getValueAt(row_index, 4)== null){
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Article Cost can not be null.");
                        table.setValueAt(article.getCoutAchatParUnite(), row_index, 4);
                    }else{
                        try{
                            double cout = Double.parseDouble(table.getValueAt(row_index, 4).toString());
                            article.setCoutAchatParUnite(cout);
                            article.setPrixVenteParUnite();
                            table.setValueAt(article.getPrixVenteParUnite(), row_index, 3);
                            Globals.map_articles.put(article.getNoArticle(), article);
                            MyFunctions.AddSortedDataArticles();
                            lbl_error.setText("");
                        }catch(NumberFormatException eee){
                            lbl_error.setForeground(Color.red);
                            lbl_error.setText("Cost must be a Double.");
                        }
                    }
                } else {
                    //new article
                    if(table.getValueAt(row_index, 4)== null){
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Article Cost can not be null.");
                    } else {
                        try{
                            double cout = Double.parseDouble(table.getValueAt(row_index, 4).toString());
                            String str_art_nom = "Article Name";
                            table.setValueAt(str_art_nom, row_index, 1);
                            table.setValueAt(0, row_index, 2);
                            table.setValueAt(0.0, row_index, 5);
                            
                            article = new Article(str_art_nom, 0.0);
                            table.setValueAt(article.getNoArticle(), row_index, 0);
                            
                            article.setCoutAchatParUnite(cout);
                            article.setPrixVenteParUnite();
                            table.setValueAt(article.getPrixVenteParUnite(), row_index, 3);
                            
                            article.setCategorieArticle(categorie);
                            Globals.map_articles.put(article.getNoArticle(), article);
                            MyFunctions.AddSortedDataArticles();
                            lbl_error.setText("");
                        }
                        catch(NumberFormatException eee){
                            lbl_error.setForeground(Color.red);
                            lbl_error.setText("Cost must be a Double.");
                        }
                        
                    }//end if cout not null
                    
                }// end if new article
                
            }//end if row_index != -1
        }
        
        
        @Override
        public void editingStopped(ChangeEvent e) {
            CheckTextChanged();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            CheckTextChanged();
        }
    
    }
    
    
    
    
            
    private class TabTextNomListener implements CellEditorListener{

        public void CheckValueChanged(){
            int row_index = table.getSelectedRow();
            if(row_index!=-1){
                if(table.getValueAt(row_index, 0)!= null){
                    //old article
                    int no_article = Integer.parseInt(table.getValueAt(row_index,0).toString());
                    article = Globals.map_articles.get(no_article);
                    if(table.getValueAt(row_index, 1)== null){
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Article Name can not be null.");
                        table.setValueAt(article.getNomArticle(), row_index, 1);
                    }else{
                        article.setNom(table.getValueAt(row_index, 1).toString());
                        Globals.map_articles.put(article.getNoArticle(), article);
                        MyFunctions.AddSortedDataArticles();
                        lbl_error.setText("");
                    }
                } else {
                    //new article
                    if(table.getValueAt(row_index, 1)== null){
                        lbl_error.setForeground(Color.red);
                        lbl_error.setText("Article Name can not be null.");
                    } else {
                        String str_art_nom = table.getValueAt(row_index, 1).toString();
                        table.setValueAt(0.0, row_index, 4);
                        table.setValueAt(0.0, row_index, 5);
                        table.setValueAt(0.0, row_index, 3);
                        table.setValueAt(0, row_index, 2);
                        
                        article = new Article(str_art_nom, 0.0);
                        article.setCategorieArticle(categorie);
                        table.setValueAt(article.getNoArticle(), row_index, 0);
                        
                        Globals.map_articles.put(article.getNoArticle(), article);
                        MyFunctions.AddSortedDataArticles();
                        lbl_error.setText("");
                    }
                }
                
            }//end if row_index != -1
        }

        @Override
        public void editingStopped(ChangeEvent e) {
            CheckValueChanged();
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            CheckValueChanged();
        }
    
    }
    
    
    
    
    
    private class TableSelectionListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int row_index = table.getSelectedRow();
            
            if(categorie == null){
                if(!table.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(null, "No Category from List is Selected", "Error" ,JOptionPane.WARNING_MESSAGE);
                    table.clearSelection();
                    
                    table.getCellEditor(row_index, 1).cancelCellEditing();
                    table.getCellEditor(row_index, 4).cancelCellEditing();
                    table.getCellEditor(row_index, 5).cancelCellEditing();
                }
            } else{
              if(row_index !=-1){
                  txt_tab_nom.setEnabled(true);
                  txt_tab_cout.setEnabled(true);
                  txt_tab_profit.setEnabled(true);

                  if(table.getValueAt(row_index,0)!=null){
                      int no_article = Integer.parseInt(table.getValueAt(row_index, 0).toString());
                      article = Globals.map_articles.get(no_article);
                      try{
                          double cout = 0.0, profit = 0.0;
                          if(table.getValueAt(row_index,4).toString()!=null){
                             cout = Double.parseDouble(table.getValueAt(row_index,4).toString());
                          } else {
                            table.setValueAt(0.0, row_index, 4);
                          }
                          
                          if(table.getValueAt(row_index, 5).toString()!= null){
                              profit = Double.parseDouble(table.getValueAt(row_index, 5).toString());
                          } else {
                              table.setValueAt(0.0, row_index, 5);
                          }
                          
                          lbl_error.setText("");
                      } catch(NumberFormatException eee){
                          lbl_error.setForeground(Color.red);
                          lbl_error.setText("Cout and Profit must be doubles.");
                      }
                      
                  } else {
                      article = null;
                  }
                  
              }//end if row_index != -1

            }// end if article not null
        }
        
    }
    
    
    
    
    
    private class ListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            lbl_error.setText("");
            //set values of Categorie Article
            if(list_categories.getSelectedIndex() != -1){
                categorie = (CategorieArticle) list_categories.getSelectedValue();
                txt_no.setText(""+categorie.getNoCategorie());
                txt_nom.setText(""+categorie.getNomCategorie());
                
                txt_nom.setEnabled(true);
                txt_nom.setBackground(Color.white);
                
                RefreshTable();
                
            } else { categorie = null; }
        }
    }
    
    
    
    
      
    private void RefreshTable(){
        tableModel = MyFilter.FilterArticlesByCategorie(tableModel,categorie);
        table.setModel(tableModel);
    }
  
    
    
    
    
    private class BtnSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String currentnb = CategorieArticle.getNoSerie()+"";
            //check if all values are not null
            if(txt_nom.getText()!=""){
                   
                if(currentnb.equals(txt_no.getText())){
                    //if new Category
                    categorie = new CategorieArticle();
                    categorie.setNomCategorie(txt_nom.getText());
                    Globals.map_categoriesArticles.put(categorie.getNoCategorie(), categorie);
                      
                    for(int i=0; i<mod_categories.getSize(); i++){
                        if(mod_categories.getElementAt(i).toString().equals(categorie.toString())){
                            list_categories.setSelectedIndex(i);
                            categorie = (CategorieArticle) list_categories.getSelectedValue();
                            break;
                        }
                    }
                  
                }
                else {
                    //if old categorie
                    Globals.map_categoriesArticles.get(categorie.getNoCategorie()).setNomCategorie(txt_nom.getText());
                    categorie.setNomCategorie(txt_nom.getText());
                }
                    
                //sort again
                ArrayList<CategorieArticle> arr_sort = new ArrayList();
                    
                Iterator it = Globals.map_categoriesArticles.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer,CategorieArticle> pair = (Map.Entry<Integer,CategorieArticle>)it.next();
                    arr_sort.add(pair.getValue());
                }
                    
                Collections.sort(arr_sort);
                    
                Globals.map_categoriesArticles = new LinkedHashMap();
                    
                mod_categories = new DefaultListModel();
                    
                int i=0;
                    
                it = arr_sort.iterator();
        
                while(it.hasNext()){
                    CategorieArticle c = (CategorieArticle)it.next();
                    Globals.map_categoriesArticles.put(c.getNoCategorie(), c);
                    mod_categories.add(i,c);
                    i++;
                }
                    
                    
                MyFunctions.AddData(Globals.FileName.CategoriesArticles, Globals.map_categoriesArticles);
                Globals.getFileCategorieArticle();

                list_categories.setModel(mod_categories);
                list_categories.revalidate();

                
                //show success message
                lbl_error.setForeground(Color.GREEN);
                lbl_error.setText("The data was successfully saved.");
                    
            } else {
                JOptionPane.showMessageDialog(null, "Fill in all fields before saving.", "Error", JOptionPane.ERROR_MESSAGE);
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
        }
    }
    
    
    
    
     
    private class BtnCreateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_error.setText("");
            
            String noSerie = ""+CategorieArticle.getNoSerie();
            txt_no.setText(noSerie);
            txt_nom.setText("");
            txt_nom.setEnabled(true);
            txt_nom.setBackground(Color.WHITE);
            
            txt_nom.requestFocusInWindow();
            list_categories.setSelectedIndex(-1);
        }
    }

        
        
        
    
}
