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
    protected JFrame f;
    
    public Vue ( JFrame f )
    {
        super();
        this.f = f;
        this.c = null;
    }
    public Vue ( Controleur c )
    {
        super();
        this.f = null;
        this.c = c;
    }
       
    @Override
    public abstract void update(Observable o, Object ob); 
}
