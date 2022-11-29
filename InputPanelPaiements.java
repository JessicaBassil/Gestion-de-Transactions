package gestiondetransactionscommerciales;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.CENTER;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class InputPanelPaiements extends JPanel{
    private JPanel pan_north, pan_south, pan_left, pan_right, pan_in_data, pan_combo, pan_list, pan_in_cont, pan_radio_btns;
    private JButton btn_creer, btn_enregistrer, btn_quitter;
    private String str_title;
    private DataMenu data_menu;
    private TitledBorder border;
    private JLabel lbl_no, lbl_desc, lbl_date, lbl_montant, lbl_fournisseurs, lbl_modePaie;
    private JTextField txt_no, txt_desc, txt_date, txt_montant;
    private JLabel lbl_error;
      
    private JComboBox<Fournisseur> combo_fournisseurs;
    private ButtonGroup group;
    private JRadioButton rad_cash, rad_cheque, rad_transfert;
    
    private Fournisseur fournisseur;
    private Paiement paiement;
    
    private JList<Paiement> list_paiements;
    private DefaultListModel mod_paiements;
    
    private Transaction.TypeP typePaie;
    
    
    
    
    
    public InputPanelPaiements(DataMenu data_menu) {
        Globals.getFileFournisseur();
        Globals.getFilePaiements();
        
        
        //assignments
        pan_north = new JPanel();
        pan_south = new JPanel();
        pan_left = new JPanel();
        pan_right = new JPanel();
        pan_in_data = new JPanel();
        pan_combo = new JPanel();
        pan_list = new JPanel();
        pan_in_cont = new JPanel();
        pan_radio_btns = new JPanel();
        
        btn_creer = new JButton("Creer");
        btn_enregistrer = new JButton("Enregistrer");
        btn_quitter = new JButton("Quitter");
        
        lbl_no = new JLabel("No Paiement", SwingConstants.RIGHT);
        lbl_desc = new JLabel("Description", SwingConstants.RIGHT);
        lbl_date = new JLabel("Date Paiement", SwingConstants.RIGHT);
        lbl_montant = new JLabel("Montant Paiement", SwingConstants.RIGHT);
        lbl_fournisseurs = new JLabel("Choisir Fournisseur", SwingConstants.CENTER);
        lbl_modePaie = new JLabel("Mode Paiement", SwingConstants.RIGHT);
        
        txt_no = new JTextField(19);
        txt_desc = new JTextField(19);
        txt_date = new JTextField(19);
        txt_montant = new JTextField(19);
        
        Border txt_border = txt_no.getBorder();
        Border actual_txt_border = BorderFactory.createCompoundBorder(txt_border, BorderFactory.createEmptyBorder(2, 0, 2, 0));
        txt_no.setBorder(actual_txt_border);
        txt_desc.setBorder(actual_txt_border);
        txt_date.setBorder(actual_txt_border);
        txt_montant.setBorder(actual_txt_border);        
        
        lbl_error = new JLabel();
        
        combo_fournisseurs = new JComboBox();
        list_paiements = new JList();
        mod_paiements = new DefaultListModel();
        
        rad_cash = new JRadioButton("Cash");        
        rad_cheque = new JRadioButton("Cheque");   
        rad_transfert = new JRadioButton("Transfert");        
        
        //Data assignments
        typePaie = Transaction.TypeP.CASH;
        this.data_menu = data_menu;
        str_title = "Paiements";
        border = BorderFactory.createTitledBorder(null, "Infos "+str_title, CENTER, 0);
        
        //add values to combo boxes
        combo_fournisseurs = MyFunctions.AddDataToComboBox(Globals.map_fournisseurs);
        
        
        //group of radio btns
        group = new ButtonGroup();
        group.add(rad_cash);
        group.add(rad_cheque);
        group.add(rad_transfert);
        
        
        //GUI design
        this.setBorder(border);
        
        this.setLayout(new GridLayout(2,1,10,10));
        this.add(pan_north);
        this.add(pan_south);
        
        pan_north.setLayout(new GridLayout(1,2,10,10));
        pan_north.add(pan_left);
        pan_north.add(pan_right);
        
        
        
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
       
        Font font = new Font("Verdana", Font.PLAIN, 9);
        rad_cash.setFont(font);
        rad_cheque.setFont(font);
        rad_transfert.setFont(font);
        
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
        
        gbc.gridy = 5;
        pan_in_data.add(lbl_modePaie, gbc);
        
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
        
        
        gbc.gridy = 5;
        pan_in_data.add(pan_radio_btns,gbc);
        
        
        pan_radio_btns.add(rad_cash);
        pan_radio_btns.add(rad_cheque);
        pan_radio_btns.add(rad_transfert);
        
        
        pan_combo.setLayout(new GridLayout(1,2,10,10));
        pan_combo.add(lbl_fournisseurs);
        pan_combo.add(combo_fournisseurs);
        
        border = BorderFactory.createTitledBorder(null, str_title, CENTER, 0);
        pan_list.setBorder(border);
        pan_list.setBackground(Color.white);
        pan_list.add(list_paiements);
        
        
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
        rad_cash.setEnabled(false);
        rad_cheque.setEnabled(false);
        rad_transfert.setEnabled(false);
        
        group.setSelected(rad_cash.getModel(), true);
        
        
        //add ActionListeners
        combo_fournisseurs.addActionListener(new FournisseurComboListener());
        btn_creer.addActionListener(new BtnCreateListener());
        btn_enregistrer.addActionListener(new BtnSaveListener());
        btn_quitter.addActionListener(new BtnQuitListener());
        list_paiements.addListSelectionListener(new ListListener());
        
        rad_cash.addActionListener(new RadioButtonListener());
        rad_cheque.addActionListener(new RadioButtonListener());
        rad_transfert.addActionListener(new RadioButtonListener());
    }
    
    
    
    
    
    private class RadioButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source.equals(rad_cash)) { typePaie =Transaction.TypeP.CASH;}
        if(source.equals(rad_cheque)) { typePaie =Transaction.TypeP.CHEQUE;}
        if(source.equals(rad_transfert)) { typePaie =Transaction.TypeP.TRANSFERT;}
        }
    }
    
    
    
    
        
    private class FournisseurComboListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            lbl_error.setText("");
            list_paiements.setSelectedIndex(-1);
        
            int nb_fournisseur = combo_fournisseurs.getSelectedIndex();
            
            //if new don't disable the txt field
            String nb_serial_v = Transaction.getNoSerie()+"";
            if(!txt_no.getText().equals(nb_serial_v)){ 
                rad_cash.setEnabled(false);
                rad_cheque.setEnabled(false);
                rad_transfert.setEnabled(false);
            }
            
            
            if(nb_fournisseur!=0){
                fournisseur =(Fournisseur) combo_fournisseurs.getSelectedItem();
            }
            else {
                fournisseur = null;
            }
            
            RefreshList();
        }
    }
    
    
    
    
    
    public void RefreshList(){
        mod_paiements = MyFilter.FilterPaiementsByFournisseur(fournisseur);
        list_paiements.setModel(mod_paiements);
        list_paiements.revalidate();
    }

    
    
   
    
    private class BtnCreateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            lbl_error.setText("");
            
            String noSerie = ""+Transaction.getNoSerie();
            txt_no.setText(noSerie);
            txt_desc.setText(Transaction.Description.Paiement.toString());
            
            String str_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            txt_date.setText(str_date);
            
            rad_cash.setEnabled(true);
            rad_cheque.setEnabled(true);
            rad_transfert.setEnabled(true);
            
            group.setSelected(rad_cash.getModel(), true);
            
            txt_montant.setText("0.00");
            list_paiements.setSelectedIndex(-1);
        }
    }
    
    
 
    
    
    private class BtnSaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(fournisseur == null){
                //Error Handling : A Fournisseur must be selected in order to continue
                JOptionPane.showMessageDialog(null, "No Fournisseur selected.", "Error" ,JOptionPane.WARNING_MESSAGE);
                combo_fournisseurs.requestFocusInWindow();
            } 
            else {
                
                try{
                    int index = list_paiements.getSelectedIndex();
                    
                    //get the date 
                    String str_date = txt_date.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(str_date, formatter);
                    
                    String currentnb = Transaction.getNoSerie()+"";
                    
                    if(currentnb.equals(txt_no.getText())){
                        //if new paiement
                        paiement = new Paiement(date, Double.parseDouble(txt_montant.getText()), fournisseur);
                        paiement.setTypePaiement(typePaie);
                        Globals.map_paiements.put(paiement.getNoTrans(),paiement);
                        Globals.setSerialNbs();
                        
                        
                        for(int i=0; i<mod_paiements.getSize(); i++){
                            if(mod_paiements.getElementAt(i).toString().equals(paiement.toString())){
                                list_paiements.setSelectedIndex(i);
                                paiement = (Paiement) list_paiements.getSelectedValue();
                                break;
                            }
                        }
                    
                    }
                    else {
                        //if old Paiement
                        paiement = Globals.map_paiements.get(Integer.parseInt(txt_no.getText().toString()));
                        Globals.map_paiements.get(paiement.getNoTrans()).setTypePaiement(typePaie);
                    }
                    
                    //sort again
                    MyFunctions.AddSortedDataPaiements();
                    Globals.getFilePaiements();
                    RefreshList();
                    
                    list_paiements.setSelectedIndex(index);
                    list_paiements.revalidate();
                    
                    //show success message
                    lbl_error.setForeground(Color.GREEN);
                    lbl_error.setText("The data was successfully saved.");
                }
                catch(NullPointerException ex3){
                    lbl_error.setForeground(Color.red);
                    lbl_error.setText("Choose a 'Paiement' first!");
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
            //set values of Paiement
            if(list_paiements.getSelectedIndex() != -1){
                paiement = (Paiement) list_paiements.getSelectedValue();
                txt_no.setText(""+paiement.getNoTrans());
                txt_desc.setText(""+paiement.getDesc());
                txt_date.setText(""+paiement.getDateTrans());
                txt_montant.setText(""+paiement.getMontant());
                
                if(paiement.getTypePaiement() == Transaction.TypeP.CASH) { group.setSelected(rad_cash.getModel(), true);}
                if(paiement.getTypePaiement() == Transaction.TypeP.CHEQUE) { group.setSelected(rad_cheque.getModel(), true);}
                if(paiement.getTypePaiement() == Transaction.TypeP.TRANSFERT) { group.setSelected(rad_transfert.getModel(), true);}
                
                rad_cash.setEnabled(true);
                rad_cheque.setEnabled(true);
                rad_transfert.setEnabled(true);
                
            } else { paiement = null; }
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

