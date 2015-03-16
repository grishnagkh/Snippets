package at.grish.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Log {

    private PrintStream out;

    public Log() {        
        out = System.out;
    }

    public Log(File file) throws FileNotFoundException {
        out = file == null ? System.out : new PrintStream(file);
    }
    
    public void d(String tag, String msg) {
        out.println("<" + tag + ">" + " " + msg);
    }
    
    public void save(){
        out.flush();
    }
}
