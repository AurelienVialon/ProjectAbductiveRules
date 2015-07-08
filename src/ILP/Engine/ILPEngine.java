/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILP.Engine;

import ILP.ILPManager;
import ILP.Predicats;
import MVC.Modele;
import PrologParse.Clause;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurélien Vialon
 */
public class ILPEngine extends Modele
{
    //Faudra sortir le manager de là après !!!!!
    public ILPManager pm;
    //
    
    public Predicats predicats;
    
    private Process pp;
    protected ILPMemory mem;
    
    private String ILPEnginePath = "/home/aurelien/Bureau/Progol/source/progol";//"/usr/jc/bin/progol";
    protected String output = "";
    
    public ILPEngine ()
    {
        super();
        this.mem = new ILPMemory ();
    }
    
    public String getILPEnginePath() 
    {
        return ILPEnginePath;
    }

    public void setILPEnginePath(String ILPEnginePath) 
    {
        this.ILPEnginePath = ILPEnginePath;
    }
    
    @Override
    public void Reinit() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void destroy ()
    {
        pp.destroy();
    }
    
    @Override
    protected void Maj() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
     
    @Override
    public void run() 
    {
      mem.Save("tmppgli.pl");
      Runtime now = Runtime.getRuntime();

      try 
      {
        while(true)
        {
            pp = now.exec(this.getILPEnginePath() + " tmppgli");
           
            InputStream s = pp.getInputStream();
            BufferedReader in
            = new BufferedReader(new InputStreamReader(s));
        
            this.ParseResults(in);
            
            //TODO !!!!!
            //this.mem.pm.UpdateResults();

            ArrayList<String> temp = this.predicats.getListPredicat();
       
            for ( String st : temp )
                this.mem.clauses.addElement(new Clause(st));
        
            String fileName = this.mem.getFileName().replaceFirst(".pl", ".new.pl");
        
            this.mem.Save(fileName);
           
            //this.mem.pm.ilasp.givetoASP(fileName);    
            
            this.pause();
        }
      }
      catch (IOException e) 
      {
	System.out.println("Error: " + e);
      }
    }
    public void ParseResults( BufferedReader in )
    {
        String memory = "";
        String line = "";
        String lineTemp = "";
        
        predicats = new Predicats();
    
        for (;;) 
        {
            try 
            {
                line = in.readLine();
                if (line == null) break;
                output += line + "\n";
                
                if(line.contains("[Total number of clauses"))
                {
                    lineTemp = line.replace("[Total number of clauses =", "");
                    
                    lineTemp = lineTemp.substring(0, lineTemp.indexOf("]"));
                    lineTemp = lineTemp.replaceAll(" ", "");
                    
                    int it = Integer.parseInt(lineTemp);
                    
                    int ind = memory.lastIndexOf("[Learning ") + "[Learning ".length(); 

                    String token = "/";
                                
                    if(ind < "[Learning ".length())
                    {
                        ind = memory.lastIndexOf("[Generalising ") + "[Generalising ".length(); 
                        token = "(";
                    }
                    
                    String temp1 = memory.substring(ind);
                    String nom_pred = temp1.substring(0, temp1.indexOf(token));
                    
                    ArrayList<String> ts = new ArrayList<>();
                    
                    for ( int i=0; i < it; i++ )
                    {
                      int index = temp1.lastIndexOf(nom_pred);  
                      lineTemp = temp1.substring(index);
                      temp1 = temp1.substring(0, index);
                      lineTemp = lineTemp.replaceAll("\n", "");
                      
                      ts.add(lineTemp);                   
                    }
                    
                    if(!ts.isEmpty())
                    {
                        for(int n=ts.size(); n > 0 ; n --)
                        {
                            this.predicats.add(nom_pred, ts.get(n - 1)); 
                        }
                    }
                    
                    line = in.readLine();//In order to remove the space line !
                    	  output += line + "\n";
                          
                    line = in.readLine();
                    	  output += line + "\n";
                    memory = "";
                } 
                else
                {
                    memory +=  line + "\n";                         
                }
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ILPManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String [] getResult (String value)
    {
        return this.predicats.getTabPredicat(value);
    }
    
    public synchronized ILPMemory getMemory ()
    {
        return this.mem;
    }
}