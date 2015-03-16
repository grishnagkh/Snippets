
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Log {
	PrintStream out;
	boolean silent;
	public Log(boolean silent){
		this.silent = silent;
		out = System.out;
	}
	public Log(File file) throws FileNotFoundException{
		
		out = file == null ? System.out : new PrintStream(file);
	}
	//debug
	public void d(String tag, String msg){
		if(!silent)
			out.println("<" + tag + ">" + " " + msg);
	}
}
