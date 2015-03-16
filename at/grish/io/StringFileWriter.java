package at.grish.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class which manages File Writing behaviour
 * 
 * @author stef
 * 
 */
public class StringFileWriter {
	/**
	 * Method for Writing a textfile to the hard disk
	 * 
	 * @param str
	 *            the text to write
	 * @param f
	 *            the file to write to
	 * @throws IOException
	 */
	public static void writeToFile(String str, File f) throws IOException {
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.flush();
		fw.flush();
		bw.close();
		fw.close();
	}

	public static String readString(File f) throws IOException {
		StringBuffer sb = new StringBuffer();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);

		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}

		return sb.toString();
	}
}