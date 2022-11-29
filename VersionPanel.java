/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondetransactionscommerciales;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.CENTER;

public class VersionPanel extends JPanel{

    private DataMenu data_menu;
    private JPanel pan_btn, pan_version;
    private JTextArea txt_version, txt_versionTitle;
    private JButton btn_quitter;
    private TitledBorder border;
    private Font font_desc, font_title;
    
    public VersionPanel(DataMenu data_menu) {
        this.data_menu = data_menu;
        
        txt_versionTitle = new JTextArea("Version 1.0 \r\nRelease Date : 2020-03-22");
        txt_version = new JTextArea("The main entities and features concerned by the application are:\r\n" 
                + "~ The customer on which the sales and receipts depend,\r\n" 
                + "~ The supplier on which purchases and payments depend,\r\n"
                + "~ The article that represents the goods or the stock,\r\n"
                + "~ The class model that meets these needs,\r\n"
                + "~ Graphical interfaces that allow the user to enter and manipulate,\r\n"
                + "~ Application information,\r\n" 
                + "~ Saving this information on the storage medium."
            );
    
        border = BorderFactory.createTitledBorder(null, "Version", CENTER, 0);
        btn_quitter = new JButton("Quit");
        font_desc = new Font("Verdana", Font.ITALIC, 12);
        font_title = new Font("Verdana", Font.BOLD, 13);
        
        pan_btn = new JPanel();
        pan_version = new JPanel();
        pan_version.setBackground(Color.white);
    
        txt_versionTitle.setEditable(false);
        txt_version.setEditable(false);
        txt_version.setFont(font_desc);
        txt_versionTitle.setFont(font_title);
        txt_version.setForeground(Color.black);
        txt_versionTitle.setForeground(Color.blue);
        
        pan_version.setLayout(new BorderLayout());
        pan_version.add(txt_versionTitle, BorderLayout.PAGE_START);
        pan_version.add(txt_version, BorderLayout.WEST);
        
        this.setBorder(border);
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        
        this.add(pan_btn, BorderLayout.EAST);
        this.add(pan_version, BorderLayout.CENTER);
    
        pan_btn.setBackground(Color.white);
        pan_btn.setLayout(new BorderLayout());
        pan_btn.add(btn_quitter, BorderLayout.NORTH);
        
        btn_quitter.addActionListener(new BtnQuitListener());
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
