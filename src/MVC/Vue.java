/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;


import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
/**
 *
 * @author Aurélien Vialon
 */
public abstract class Vue extends JPanel implements Observer
{
    protected Controleur c;
    
    public Vue ( )
    {
        super();
        this.c = null;
    }
    public Vue ( Controleur c )
    {
        super();
        this.c = c;
    }
       
    @Override
    public abstract void update(Observable o, Object ob); 
}
