package gestiondetransactionscommerciales;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 * A class containing all filters
 */
public class MyFilter {

    
    
    
    
    /**
     * A function that filters 'Ventes' by Client.
     * Gets all Sales for a certain client.
     * @param client Client
     * @return filtered model
     */
    public static DefaultListModel FilterVentesByClient(Client client){
        DefaultListModel mod = new DefaultListModel();
        
        if(client != null){
            LinkedHashMap<Integer,Vente> map_filter = Globals.map_ventes;

            if(!map_filter.isEmpty()){
                Iterator it = map_filter.entrySet().iterator();
                int i=0;
                while (it.hasNext()) {
                    Map.Entry<Integer,Vente> pair = (Map.Entry<Integer,Vente>)it.next();
                    if(pair.getValue().getClient().getNoCompte() == client.getNoCompte()){
                        mod.add(i,pair.getValue());
                        i++;
                    }
                }
            }
        }
        return mod;
    }
    
    
    
    
    
    
    /**
     * A function that filters 'Achats' by 'Fournisseur'.
     * Gets all Buys for a certain Seller.
     * @param fourn 'Fournisseur'
     * @return filtered model
     */
    public static DefaultListModel FilterAchatsByFournisseur(Fournisseur fourn){
        DefaultListModel mod = new DefaultListModel();

        if(fourn != null){
            LinkedHashMap<Integer,Achat> map_filter = Globals.map_achats;

            if(!map_filter.isEmpty()){
                Iterator it = map_filter.entrySet().iterator();
                int i=0;
                while (it.hasNext()) {
                    Map.Entry<Integer,Achat> pair = (Map.Entry<Integer,Achat>)it.next();
                    if(pair.getValue().getFournisseur().getNoCompte() == fourn.getNoCompte()){
                        mod.add(i,pair.getValue());
                        i++;
                    }
                }
            }
        }
        return mod;
    }
    
    
    
    
    
    
    /**
     * A function that filters 'Recus' by 'Client'.
     * @param client 'client'
     * @return filtered model
     */
    public static DefaultListModel FilterRecusByClient(Client client){
        DefaultListModel mod = new DefaultListModel();

        if(client != null){
            LinkedHashMap<Integer,Recu> map_filter = Globals.map_recus;

            if(!map_filter.isEmpty()){
                Iterator it = map_filter.entrySet().iterator();
                int i=0;
                while (it.hasNext()) {
                    Map.Entry<Integer,Recu> pair = (Map.Entry<Integer,Recu>)it.next();
                    if(pair.getValue().getClient().getNoCompte() == client.getNoCompte()){
                        mod.add(i,pair.getValue());
                        i++;
                    }
                }
            }
        }
        return mod;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * A function that filters 'Paiements' by 'Fournisseur'.
     * @param fournisseur 'fournisseur'
     * @return filtered model
     */
    public static DefaultListModel FilterPaiementsByFournisseur(Fournisseur fournisseur){
        DefaultListModel mod = new DefaultListModel();

        if(fournisseur != null){
            LinkedHashMap<Integer,Paiement> map_filter = Globals.map_paiements;

            if(!map_filter.isEmpty()){
                Iterator it = map_filter.entrySet().iterator();
                int i=0;
                while (it.hasNext()) {
                    Map.Entry<Integer,Paiement> pair = (Map.Entry<Integer,Paiement>)it.next();
                    if(pair.getValue().getFournisseur().getNoCompte() == fournisseur.getNoCompte()){
                        mod.add(i,pair.getValue());
                        i++;
                    }
                }
            }
        }
        return mod;
    }
    
    
    
    
    
    
    /**
     * A function that filters 'Articles' by 'CategorieArticle'.
     * @param cat_art 'Categorie' 'Article'
     * @return filtered list model
     */
    public static DefaultListModel FilterArticlesByCategorie(CategorieArticle cat_art){
        DefaultListModel mod = new DefaultListModel();

        if(cat_art != null){
            LinkedHashMap<Integer,Article> map_filter = Globals.map_articles;

            if(!map_filter.isEmpty()){
                Iterator it = map_filter.entrySet().iterator();
                int i=0;
                while (it.hasNext()) {
                    Map.Entry<Integer,Article> pair = (Map.Entry<Integer,Article>)it.next();
                    if(pair.getValue().getCategorieArticle().getNoCategorie() == cat_art.getNoCategorie()){
                        mod.add(i,pair.getValue());
                        i++;
                    }
                }
            }
        }
        return mod;
    }
    
    
    
    
    
    /**
     * A function that filter a collection of 'Article' by 'CategorieArticle'.
     * @param model
     * @param cat_art Category Article
     * @return filtered table model
     */
    public static DefaultTableModel FilterArticlesByCategorie(DefaultTableModel model,CategorieArticle cat_art){
        model.setRowCount(0);
        
        if(cat_art != null && !Globals.map_articles.isEmpty()){
            Iterator it = Globals.map_articles.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Integer,Article> pair = (Map.Entry<Integer, Article>)it.next();
                if(pair.getValue().getCategorieArticle().getNoCategorie() == cat_art.getNoCategorie()){
                    Object[]data ={
                        pair.getValue().getNoArticle()+"",
                        pair.getValue().getNomArticle()+"",
                        pair.getValue().getQteStock()+"",
                        pair.getValue().getPrixVenteParUnite()+"",
                        pair.getValue().getCoutAchatParUnite()+"",
                        pair.getValue().getTauxProfit()+""
                    };
                    model.addRow(data);
                }//end if
                
            }//end while
            
        }//end if
            if(model.getRowCount()<20){
                int add = 30 - model.getRowCount();
                model.setRowCount(model.getRowCount()+add);
            }
        return model;
    
    }
    
    
    
    
    
    /**
     * A function that filters a collection of 'DetailVente' by 'Vente'.
     * @param model
     * @param vente 'Vente'
     * @return filtered collection in a Table Model.
     */
    public static DefaultTableModel FilterDetailVenteByVente(DefaultTableModel model,Vente vente){
        model.setRowCount(0);

        if(vente != null && !Globals.map_detailsVente.isEmpty()){
            
            Iterator it = Globals.map_detailsVente.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,DetailVente> pair = (Map.Entry)it.next();
                
                if(pair.getValue().getVente().getNoTrans() == vente.getNoTrans()){
                    Object[]data ={
                            pair.getValue().getArticle(),
                            pair.getValue().getQuantite()+"",
                            pair.getValue().getPrixVente(),
                            pair.getValue().calculerMontant()+""
                        };
                
                    model.addRow(data);
                
                }
            }
            
        }
        
            if(model.getRowCount()<20){
                int add = 30 - model.getRowCount();
                model.setRowCount(model.getRowCount()+add);
            }
            
        return model;
    }
    
    
    
    
    
    
    /**
     * A function that filters an ArrayList of 'DetailAchat' by 'Achat'.
     * @param Achat 'achat'
     * @return filtered ArrayList
     */
    public static DefaultTableModel FilterDetailAchatByAchat(DefaultTableModel model,Achat achat){
                model.setRowCount(0);

        if(achat != null && !Globals.map_detailsAchat.isEmpty()){
            
            Iterator it = Globals.map_detailsAchat.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,DetailAchat> pair = (Map.Entry)it.next();
                if(pair.getValue().getAchat().getNoTrans() == achat.getNoTrans()){
                    Object[]data ={
                            pair.getValue().getArticle(),
                            pair.getValue().getQuantite()+"",
                            pair.getValue().getCout()+"",
                            pair.getValue().calculerMontant()+""
                        };
                
                    model.addRow(data);
                
                }
            }
            
        }
        
            if(model.getRowCount()<20){
                int add = 30 - model.getRowCount();
                model.setRowCount(model.getRowCount()+add);
            }
            
        return model;

    }
    

    
    
    
    
    /**
     * A function that filters 'TransactionClient' by 'Client'.
     * @param model ArrayList to filter
     * @param client 'client'
     * @return filtered TableModel
     */
    public static DefaultTableModel FilterTransactionClientByClient(DefaultTableModel model, Client client){
        model.setRowCount(0);
        
        if(client != null && !Globals.map_recus.isEmpty()){            
            Iterator it = Globals.map_recus.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,Recu> pair = (Map.Entry)it.next();
                if(pair.getValue().getClient().getNoCompte() == client.getNoCompte()){
                    Object[]data ={
                            pair.getValue().getNoTrans()+"",
                            pair.getValue().getDesc()+"",
                            pair.getValue().getDateTrans()+"",
                            pair.getValue().getMontant()+""
                        };
                
                    model.addRow(data);
                    
                }
            }
            
        }
        
        if(client !=null && !Globals.map_ventes.isEmpty()){
                    
            Iterator it = Globals.map_ventes.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,Vente> pair = (Map.Entry)it.next();
                if(pair.getValue().getClient().getNoCompte() == client.getNoCompte()){
                    Object[]data ={
                            pair.getValue().getNoTrans()+"",
                            pair.getValue().getDesc()+"",
                            pair.getValue().getDateTrans()+"",
                            pair.getValue().getMontant()+""
                        };
                
                    model.addRow(data);
                }
            }
        }
        
            if(model.getRowCount()<20){
                int add = 30 - model.getRowCount();
                model.setRowCount(model.getRowCount()+add);
            }
        return model;
    }
    
    
    
    
 
    
        
    
    

    
    /**
     * A function that filters 'TransactionFournisseur'('Achat' and 'Paiement') by 'Fournisseur'.
     * @param fourn 'Fournisseur'
     * @param model Table model 
     * @return filtered
     */
    public static DefaultTableModel FilterTransactionFournisseurByFournisseur(DefaultTableModel model, Fournisseur fourn){
        model.setRowCount(0);
                
        if(fourn !=null && !Globals.map_achats.isEmpty()){
                    
            Iterator it = Globals.map_achats.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,Achat> pair = (Map.Entry)it.next();
                if(pair.getValue().getFournisseur().getNoCompte() == fourn.getNoCompte()){
                    Object[]data ={
                            pair.getValue().getNoTrans()+"",
                            pair.getValue().getDesc()+"",
                            pair.getValue().getDateTrans()+"",
                            pair.getValue().getMontant()+""
                        };
                
                    model.addRow(data);
                }
            }
        }
                
                
        if(fourn != null && !Globals.map_paiements.isEmpty()){            
            Iterator it = Globals.map_paiements.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,Paiement> pair = (Map.Entry)it.next();
                if(pair.getValue().getFournisseur().getNoCompte() == fourn.getNoCompte()){
                    Object[]data ={
                            pair.getValue().getNoTrans()+"",
                            pair.getValue().getDesc()+"",
                            pair.getValue().getDateTrans()+"",
                            pair.getValue().getMontant()+""
                        };
                
                    model.addRow(data);
                    
                }
            }
            
        }
        

        model.setRowCount(20);
        return model;

     }
    
    
    
    
    
    /**
     * A function that filters 'DetailVente' and 'DetailAchat' by 'Article'.
     * @param model original model to be changed
     * @param article filter
     * @return filtered model
     */
    public static DefaultTableModel FilterDetailsByArticle(DefaultTableModel model, Article article){
        model.setRowCount(0);
                
        if(article !=null && !Globals.map_detailsAchat.isEmpty()){
                    
            Iterator it = Globals.map_detailsAchat.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,DetailAchat> pair = (Map.Entry)it.next();
                if(pair.getValue().getArticle().getNoArticle()== article.getNoArticle()){
                    Object[]data ={
                            pair.getValue().getAchat().getNoTrans()+"",
                            pair.getValue().getAchat().getDesc()+"",
                            pair.getValue().getAchat().getFournisseur()+"",
                            pair.getValue().getAchat().getDateTrans()+"",
                            pair.getValue().getQuantite()+"",
                            pair.getValue().getCout()+"",
                            pair.getValue().calculerMontant()+""
                        };
                
                    model.addRow(data);
                }
            }
        }
                
                
        if(article != null && !Globals.map_detailsVente.isEmpty()){            
            Iterator it = Globals.map_detailsVente.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,DetailVente> pair = (Map.Entry)it.next();
                if(pair.getValue().getArticle().getNoArticle() == article.getNoArticle()){
                    Object[]data ={
                            pair.getValue().getVente().getNoTrans()+"",
                            pair.getValue().getVente().getDesc()+"",
                            pair.getValue().getVente().getClient()+"",
                            pair.getValue().getVente().getDateTrans()+"",
                            pair.getValue().getQuantite()+"",
                            pair.getValue().getPrixVente()+"",
                            pair.getValue().calculerMontant()+""
                        };
                
                    model.addRow(data);
                    
                }
            }
            
        }
        

        model.setRowCount(20);
        return model;

        
    }
    
    
    
    
    
}
