package gestiondetransactionscommerciales;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MyMenuBar extends JMenuBar{
    DataMenu data_menu;
    JMenu m_trans,m_compte,m_stock,m_outils,m_quitter,
            m_ventes,m_achats,m_recus,m_paiements,m_clients,m_fourn, m_articles;
    
    JMenuItem mi_villes, mi_categorie_art, mi_load, mi_store, mi_version, mi_quitter, 
            mi_in_ventes, mi_in_achats, mi_in_recus, mi_in_paiements, mi_in_clients, mi_in_fourn, mi_in_articles,
            mi_rep_ventes,  mi_rep_achats, mi_rep_recus, mi_rep_paiements, mi_rep_clients, mi_rep_fourn, mi_rep_articles;

    
    public MyMenuBar(DataMenu data_menu) {
        this.data_menu = data_menu;
        
        m_trans = new JMenu("Transactions");
        m_compte = new JMenu("Comptes");
        m_stock = new JMenu("Stock");
        m_outils = new JMenu("Outils");
        m_quitter = new JMenu("Quitter");
        
        m_ventes = new JMenu("Ventes");
        m_achats = new JMenu("Achats");
        m_recus = new JMenu("Recus");
        m_paiements = new JMenu("Paiements");
        m_clients = new JMenu("Clients");
        m_fourn = new JMenu("Fournisseurs");
        m_articles = new JMenu("Articles");
        
        mi_villes = new JMenuItem("Villes");
        mi_categorie_art = new JMenuItem("Categories Articles");
        mi_load = new JMenuItem("Charger du disque");
        mi_store = new JMenuItem("Sauvegarder sur disque");
        mi_version = new JMenuItem("Version");
        mi_quitter = new JMenuItem("Quitter");
        
        mi_in_ventes = new JMenuItem("Creer/Modifier");
        mi_in_achats = new JMenuItem("Creer/Modifier");
        mi_in_recus = new JMenuItem("Creer/Modifier");
        mi_in_paiements = new JMenuItem("Creer/Modifier");
        mi_in_clients = new JMenuItem("Creer/Modifier");
        mi_in_fourn = new JMenuItem("Creer/Modifier");
        mi_in_articles = new JMenuItem("Creer/Modifier");
        
        mi_rep_ventes = new JMenuItem("Rapports");
        mi_rep_achats = new JMenuItem("Rapports");
        mi_rep_recus = new JMenuItem("Rapports");
        mi_rep_paiements = new JMenuItem("Rapports");
        mi_rep_clients = new JMenuItem("Rapports");
        mi_rep_fourn = new JMenuItem("Rapports");
        mi_rep_articles = new JMenuItem("Rapports");
        
        
        
        this.add(m_trans);
        this.add(m_compte);
        this.add(m_stock);
        this.add(m_outils);
        this.add(m_quitter);
        
        m_trans.add(m_ventes);
        m_trans.add(m_achats);
        m_trans.add(m_recus);
        m_trans.add(m_paiements);
        
        m_compte.add(m_clients);
        m_compte.add(m_fourn);
        m_compte.add(mi_villes);
        
        m_stock.add(m_articles);
        m_stock.add(mi_categorie_art);
        
        m_outils.add(mi_load);
        m_outils.add(mi_store);
        
        m_quitter.add(mi_version);
        m_quitter.add(mi_quitter);
        
        m_ventes.add(mi_in_ventes);
        m_ventes.add(mi_rep_ventes);
        m_achats.add(mi_in_achats);
        m_achats.add(mi_rep_achats);
        m_recus.add(mi_in_recus);
        m_recus.add(mi_rep_recus);
        m_paiements.add(mi_in_paiements);
        m_paiements.add(mi_rep_paiements);
        
        m_clients.add(mi_in_clients);
        m_clients.add(mi_rep_clients);
        m_fourn.add(mi_in_fourn);
        m_fourn.add(mi_rep_fourn);
        
        m_articles.add(mi_in_articles);
        m_articles.add(mi_rep_articles);
        
        mi_in_ventes.addActionListener(new MenuBarListener());
        mi_in_achats.addActionListener(new MenuBarListener());
        mi_in_recus.addActionListener(new MenuBarListener());
        mi_in_paiements.addActionListener(new MenuBarListener());
        mi_in_clients.addActionListener(new MenuBarListener());
        mi_in_fourn.addActionListener(new MenuBarListener());
        mi_in_articles.addActionListener(new MenuBarListener());
        mi_categorie_art.addActionListener(new MenuBarListener());
        
        mi_rep_ventes.addActionListener(new MenuBarListener());
        mi_rep_achats.addActionListener(new MenuBarListener());
        mi_rep_recus.addActionListener(new MenuBarListener());
        mi_rep_paiements.addActionListener(new MenuBarListener());
        mi_rep_clients.addActionListener(new MenuBarListener());
        mi_rep_fourn.addActionListener(new MenuBarListener());
        mi_rep_articles.addActionListener(new MenuBarListener());
        
    }

  
    
    private class MenuBarListener implements ActionListener{
        
        public void actionPerformed(ActionEvent event){
            Object obj = event.getSource();
            if(obj.equals(mi_in_ventes)){ data_menu.setPanel(new InputPanelVentes(data_menu));}
            if(obj.equals(mi_in_achats)){ data_menu.setPanel(new InputPanelAchats(data_menu));}
            if(obj.equals(mi_in_recus)){ data_menu.setPanel(new InputPanelRecus(data_menu));}
            if(obj.equals(mi_in_paiements)){ data_menu.setPanel(new InputPanelPaiements(data_menu));}
            if(obj.equals(mi_in_clients)){ data_menu.setPanel(new InputPanelClients(data_menu));}
            if(obj.equals(mi_in_fourn)){ data_menu.setPanel(new InputPanelFournisseurs(data_menu));}
            if(obj.equals(mi_in_articles)){ data_menu.setPanel(new InputPanelArticles(data_menu));}
            if(obj.equals(mi_categorie_art)){ data_menu.setPanel(new InputPanelCategoriesArticles(data_menu));}
            
            if(obj.equals(mi_rep_ventes)){ data_menu.setPanel(new ReportPanelVentes(data_menu));}
            if(obj.equals(mi_rep_achats)){ data_menu.setPanel(new ReportPanelAchats(data_menu));}
            if(obj.equals(mi_rep_recus)){ data_menu.setPanel(new ReportPanelRecus(data_menu));}
            if(obj.equals(mi_rep_paiements)){ data_menu.setPanel(new ReportPanelPaiements(data_menu));}
            if(obj.equals(mi_rep_clients)){ data_menu.setPanel(new ReportPanelClients(data_menu));}
            if(obj.equals(mi_rep_fourn)){ data_menu.setPanel(new ReportPanelFournisseurs(data_menu));}
            if(obj.equals(mi_rep_articles)){ data_menu.setPanel(new ReportPanelArticles(data_menu));}
        }
    }
    
}
