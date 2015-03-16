/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.grish.parse.xml.test;

import at.grish.parse.xml.DomParser;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author stef
 */
public class XMLDOMTest {

    public static void main(String[] args) throws IOException{
        DomParser dp = new DomParser(new File("C:\\Users\\stef\\Documents\\NetBeansProjects\\Snippets\\build.xml"));
        dp.parse();
    } 
    
}
