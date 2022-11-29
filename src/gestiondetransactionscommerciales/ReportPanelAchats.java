package gestiondetransactionscommerciales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.CENTER;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class ReportPanelAchats extends JPanel{

    private JPanel pan_north, pan_south, pan_filter, pan_quitter;
    private JButton btn_ok, btn_quitter;
    private String str_title;
    private String [] a_tab_headers;
    private DataMenu data_menu;
    private TitledBorder border;
    
    private JLabel lbl_from, lbl_to, lbl_fournisseurs;
    private JTextField txt_from, txt_to;
    private JComboBox<Fournisseur> combo_fournisseurs;
    
    private JLabel lbl_error;

    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scroll;
    
    private LocalDate date_from, date_to;
    
    
    
    
    
    
    public ReportPanelAchats(DataMenu data_menu) {
        Globals.getFileAchats();
        Globals.getFileFournisseur();    
        //assignments
        pan_north = new JPanel();
        pan_south = new JPanel();
        pan_filter = new JPanel();
        pan_quitter = new JPanel();
        
        btn_ok = new JButton("OK");
        btn_quitter = new JButton("Quitter");
        
        lbl_from = new JLabel("De Date", SwingConstants.CENTER);
        lbl_to = new JLabel("A Date", SwingConstants.CENTER);
        lbl_fournisseurs = MyFunctions.ChangeWidth("Fournisseur");
        txt_from = new JTextField(5);
        txt_to = new JTextField(5);
        
        lbl_error = new JLabel();
        combo_fournisseurs = new JComboBox();
        
        //add values to combo box
        combo_fournisseurs = MyFunctions.AddDataToComboBox(Globals.map_fournisseurs);
        combo_fournisseurs.revalidate();
        
                
        //Data assignments
        this.data_menu = data_menu;
        str_title = "Rapports Achats";
        border = BorderFactory.createTitledBorder(null, str_title, CENTER, 0);
        
        a_tab_headers = new String[]{"No Achat","Date Achat","Fournisseur","Montant Achat"};
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
        
        gbc.gridx = 4;
        pan_filter.add(btn_ok, gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 2;
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridy = 0;
        pan_filter.add(lbl_fournisseurs, gbc);
        
        gbc.gridy = 1;
        pan_filter.add(combo_fournisseurs,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        pan_filter.add(lbl_error, gbc);
        
        //add ActionListeners
        btn_quitter.addActionListener(new BtnQuitListener());
        txt_from.getDocument().addDocumentListener(new DateTextListener());
        txt_to.getDocument().addDocumentListener(new DateTextListener());
        btn_ok.addActionListener(new BtnOKListener());
        
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
    
        if(!Globals.map_achats.isEmpty()){
            Fournisseur fournisseur =null;
            if(!combo_fournisseurs.getSelectedItem().equals("--Select--")){
                fournisseur = (Fournisseur)combo_fournisseurs.getSelectedItem(); 
            }
    
            tableModel.setRowCount(0);
            Iterator it = Globals.map_achats.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Integer, Achat> pair = (Map.Entry<Integer, Achat>) it.next();
                Fournisseur pair_fournisseur = pair.getValue().getFournisseur();
                if((fournisseur == null || pair_fournisseur.getNoCompte() == fournisseur.getNoCompte())
                        && (date_from==null || date_from.compareTo(pair.getValue().getDateTrans())<0)
                        && (date_to == null || date_to.compareTo(pair.getValue().getDateTrans())>0) ){
                    Object[]data ={
                        pair.getValue().getNoTrans()+"",
                        pair.getValue().getDateTrans()+"",
                        pair.getValue().getFournisseur().toString()+"",
                        pair.getValue().getMontant()+""
                    };
                    tableModel.addRow(data);
                }
            }
            
            if(tableModel.getRowCount()<40){
                int add = 40 - tableModel.getRowCount();
                tableModel.setRowCount(tableModel.getRowCount()+add);
            }
            table.setModel(tableModel);
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
    

