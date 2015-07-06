package ProgolInterface;

import ILP.ILPManager;
import PrologParse.Clause;
import myawt.GridBag;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** 
 * A Panel to allow one to run a (native) Progol process.
 * The process is run on the contents of the current 
 * ProgolInterface session.
 * One can view, edit, and save the output.
 * Part of the Progol Interface package.
 * @author Rupert Parson
 * @version 2.0
 * @see ProgolInterface
 */
class ProgolExecPanel extends Panel implements ActionListener 
{
  private final ProgolInterface session;
  private final JTextArea output;
  private final Button run, stop, save, clear;
  private Process pp;
  
  private final GridBagLayout gridbag = new GridBagLayout();

  /**
   * The constructor.
   * The parent ProgolInterface is passed to the panel
   * as a session so that the panel knows what to display
   * in its various Lists.
   * @param session  The ProgolInterface session.
   */
  public ProgolExecPanel(ProgolInterface session) 
  {
    this.session = session;
        
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
    session.saveSession("tmppgli.pl");
    Runtime now = Runtime.getRuntime();

      try 
      {
	pp = now.exec(this.session.getProgolPath() + " tmppgli");
           
	InputStream s = pp.getInputStream();
	BufferedReader in
	  = new BufferedReader(new InputStreamReader(s));
        
        this.session.pm.ParseResults(in, output);
        this.session.pm.UpdateResults();

        ArrayList<String> temp = this.session.pm.predicats.getListPredicat();
       
        for ( String st : temp )
            this.session.clauses.addElement(new Clause(st));
        
        String fileName = this.session.getFileName().replaceFirst(".pl", ".new.pl");
        
        this.session.saveSession(fileName);
        this.session.pm.ilasp.givetoASP(fileName);
        
	return 1;
      }
      catch (IOException e) 
      {
	System.out.println("Oooups: " + e);
	return 1;
      }
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
        System.out.println("Whoops: " + e.toString()); 
    }
  }
  
  /**
   * Handle Button press events.
   */
  public final void actionPerformed(ActionEvent event) {
    if (event.getSource() == run) {
      int exitvalue = runSession();
    }
	else if (event.getSource() == stop) {
	  pp.destroy();
	}
    else if (event.getSource() == clear) {
      output.setText("");
    }
    else if (event.getSource() == save) {
      FileDialog fs = new FileDialog(session.f, "Save Output", FileDialog.SAVE);
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
}
