/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASP.Manager;

import ASP.Engine.ASPEngine;
import java.io.IOException;
/**
 *
 * @author Aurélien Vialon
 */
public class ASPManager 
{    
    private ASPEngine ae;
        
    public ASPManager()
    {
	    
    }
	    

                private static String searchForExe() {
                    String[] candidates = { "dlv", "./dlv", "./dlv.bin",
                            "./dlv.exe","./dlv.i386-linux-elf-static.bin","./dlv.i386-apple-darwin.bin",
                            "./dlv.x86-64-linux-elf-static.bin","./dlv.mingw.exe"};
                    for (String candidate : candidates) 
                    {
                        boolean found = true;
                        try 
                        {
                            Process p = Runtime.getRuntime().exec(candidate);
                            p.destroy();
                        } 
                        catch (IOException ex) 
                        {
                            found = false;
                        }
                        if (found)
                            return candidate;
                    }
                    return null;
                }
}
