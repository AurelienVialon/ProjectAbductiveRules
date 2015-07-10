package ILP;

import MVC.Vue;
import myawt.GridBag;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Observable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import nomprol.FenetrePrincipale;

/** 
 * A Panel to allow one to run a (native) Progol process.
 * The process is run on the contents of the current 
 * ProgolInterface session.
 * One can view, edit, and save the output.
 * Part of the Progol Interface package.
 * @author Rupert Parson
 * @version 2.0
 */
public class ILPDisplay extends Vue 
{
  private final ILPManager manager;
  private final JTextArea output;
  private final Button run, stop, save, clear;
  
  private final GridBagLayout gridbag = new GridBagLayout();

  /**
   * The constructor.
   * The parent ProgolInterface is passed to the panel
   * as a session so that the panel knows what to display
   * in its various Lists.
   * @param session  The ProgolInterface session.
   */
  public ILPDisplay(FenetrePrincipale fen) 
  {
    super(fen);
    this.manager = ((FenetrePrincipale)this.f).ILP_Manager;
        
    this.output = new JTextArea("",24,80);

    JScrollPane sp = new JScrollPane(this.output, 
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    this.output.setEditable(false);
    this.output.setBackground(Color.white);
    
    this.run = new Button("Run Progol");
    this.run.addActionListener(this);

    this.stop = new Button("Stop Progol");
    this.stop.addActionListener(this);
    this.save = new Button("Save Output");
    this.save.addActionListener(this);
    this.clear = new Button("Clear Output");
    this.clear.addActionListener(this);

    this.setLayout(gridbag);

    GridBag.constrain(this, new Label("Output:"), 0, 0, 4, 1, 
	      GridBagConstraints.NONE, 
	      GridBagConstraints.NORTHWEST,
	      1.0, 0.0, 10, 10, 0, 10);
    GridBag.constrain(this, sp, 0, 1, 4, 1, 
	      GridBagConstraints.BOTH, 
	      GridBagConstraints.NORTHWEST,
	      1.0, 1.0, 0, 10, 10, 10);
    GridBag.constrain(this, run, 0, 2, 1, 1, 
	      GridBagConstraints.HORIZONTAL, 
	      GridBagConstraints.CENTER,
	      1.0, 0.0, 10, 10, 10, 10);		  
    GridBag.constrain(this, stop, 1, 2, 1, 1, 
	      GridBagConstraints.HORIZONTAL, 
	      GridBagConstraints.CENTER,
	      1.0, 0.0, 10, 10, 10, 10);
    GridBag.constrain(this, save, 2, 2, 1, 1, 
	      GridBagConstraints.HORIZONTAL, 
	      GridBagConstraints.CENTER,
	      1.0, 0.0, 10, 10, 10, 10);
    GridBag.constrain(this, clear, 3, 2, 1, 1, 
	      GridBagConstraints.HORIZONTAL, 
	      GridBagConstraints.CENTER,
	      1.0, 0.0, 10, 10, 10, 10);
  }

  private int runSession()
  {
      this.manager.Fonctionnement();
      return 1;
  }

  private void saveOutput(String filename) 
  {
    try 
    {
      FileWriter ws = new FileWriter(filename);
      ws.write(output.getText());
      ws.close();
    }
    catch(IOException e) 
    { 
        System.out.println("Error : " + e.toString()); 
    }
  }
  
  /**
   * Handle Button press events.
   */
  public final void actionPerformed(ActionEvent event) 
  {
    if (event.getSource() == run) 
    {
      int exitvalue = runSession();
    }
	else if (event.getSource() == stop) 
        {
	  this.manager.Stop();
	}
    else if (event.getSource() == clear) {
      output.setText("");
    }
    else if (event.getSource() == save) {
      FileDialog fs = new FileDialog(f, "Save Output", FileDialog.SAVE);
      fs.pack();
      fs.show();
      String fn = fs.getFile();
      String fd = fs.getDirectory();
      if (fn != null) { 
	File f = new File(fn,fd);
	if (f.canWrite()) { saveOutput(fd+fn); }
      }
    }
  }

    @Override
    public void update(Observable o, Object ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
