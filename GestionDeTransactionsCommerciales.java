package gestiondetransactionscommerciales;

public class GestionDeTransactionsCommerciales {

    public static void main(String[] args) {
/*
        //new InputPanelVentes();
        //new InputPanelAchats();
        //new InputPanelRecus();
        //new InputPanelPaiements();
        //new InputPanelClients();
        //new InputPanelFournisseurs();
        //new InputPanelArticles();
        //new InputPanelCategoriesArticles();
        //new InputPanelVilles();
        
        //new ReportPanelVentes();
        //new ReportPanelAchats();
        //new ReportPanelRecus();
        //new ReportPanelPaiements();
        //new ReportPanelClients();
        //new ReportPanelFournisseurs();
        //new ReportPanelArticles();
        
        //new VersionPanel();
*/
        DataMenu d = new DataMenu();
        MainFrame main_pan = new MainFrame(d);
        d.addObserver(main_pan);
    }
    
}
