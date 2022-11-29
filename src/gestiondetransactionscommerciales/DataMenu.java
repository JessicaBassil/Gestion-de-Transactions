package gestiondetransactionscommerciales;

import java.util.Observable;
import javax.swing.JPanel;

public class DataMenu extends Observable{

    JPanel panel;
    
    public void setPanel(JPanel panel){
        this.panel = panel;
        panel.repaint();
        setChanged();
        notifyObservers();
    }
    
    public JPanel getPanel(){
        return this.panel;
    }
}
