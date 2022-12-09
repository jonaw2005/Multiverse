package multiv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException {
		String filename = "testfile123.txt";
		File f = new File(filename);
		if(f.delete()) {
			System.out.println("Succesfull");
		}
		
		FileWriter g = new FileWriter(filename);
		g.write("test hat funktioniert");
		g.flush();
		g.close();
		
	}
}
