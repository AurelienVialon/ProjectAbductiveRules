/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;


import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author Aurélien Vialon
 */
public abstract class Vue extends JPanel implements Observer, ActionListener
{
    protected Controleur c;
    
    public Vue ( JFrame f )
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
