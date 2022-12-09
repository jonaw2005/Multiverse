package multiv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Scanner;

public class filing {

	public static void main(String[] args) throws IOException {
		
		/**System.out.println(getPW("a"));
		String[] x = {"world1", "world29"};
		
		extendlog("a", x);
		**/
		eraseLine(2);
		
		
		
		
	}
	
	
	
	public static String read(String filename) throws IOException {
		int ch;
		String out = "";
		FileReader fr=null; 
        fr = new FileReader(filename); 
        
        while ((ch=fr.read())!=-1) {
        	out = out + (char) ch;
        }
            
        fr.close();
        return out;
		
	}
	public static void overwrite(String filename, String msg) throws IOException {
		FileWriter fw = new FileWriter(filename);
		fw.write(msg);
		fw.close();
		System.out.println("succes");
	}
	public static void extendwrite(String filename, String msg) throws IOException {
		
		String towrite = read(filename) + "\n" + msg;
		
		FileWriter fw = new FileWriter(filename);
		fw.write(towrite);
		fw.close();
		System.out.println("succes");
	}
	public static int getLine(String filename, String a) {
		int goal = -1;
		
		
		try {

			File file=new File(filename);    //creates a new file instance  
			FileReader fr=new FileReader(file);   //reads the file  
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
			StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
			String line;  
			String[] lines = new String[30];
			int i = 0;
			
			while((line=br.readLine())!=null)  {  
			sb.append(line); 
			
			lines[i] = line;
			i = i+1;
			
			//appends line to string buffer  
			sb.append("\n");     //line feed   
			}  
			fr.close();    //closes the stream and release the resources  
			
			
			for(int x = 0; x<lines.length; x++) {
				
				
				
				if(lines[x] != null) {
					
					String[] content = lines[x].split(",");
					
					if(content[0].equals(a)) {
						goal = x;
					}
					
				}
				
			}
			
			
		}catch(Exception e) {return -1;}
		
		return goal;
	}
	public static void logtoFile(String filename, String name, String pw, String[] arg) throws IOException {
		
		String towrite = read(filename) + "\n";
		
		FileWriter fw = new FileWriter(filename);
		
		String line = name+","+pw+",";
		for(int x = 0; x< arg.length; x++) {
			
			if(arg[x] != null) {
				
				
				line = line + arg[x] + ",";
				
			}
			
			
		}
		
		towrite = towrite + line;
		
		fw.write(towrite);
		fw.close();
		
		
	}
	public static boolean isRgst(String uname) {
		
		if(getLine("a.txt", uname) == -1) {
			
			return false;
			
		}
		
		return true;
	}
	public static String[] getWorlds(String uname) {
		
		
		String content = "";
		String filename = "login.txt";
		
		int line = getLine(filename, uname);
		
		content = getContent(line);
		
		String[] contentlist = content.split(", ");
		System.out.println(Arrays.deepToString(contentlist));
		String[] worlds = new String[contentlist.length-2];
		
		for(int i = 2; i<contentlist.length; i++) {
			//System.out.println("test");
			worlds[i-2] = contentlist[i];
			System.out.println(worlds[i-2]);
			
		}
		
		return worlds;
	}
	public static String getContent(int line) {
		String content = "";
		
		int lineNumber = 0;
		 try {
			 
		      FileReader readfile = new FileReader("a.txt");
		      BufferedReader readbuffer = new BufferedReader(readfile);
		      
		      
		      for (lineNumber = 0; lineNumber < 10; lineNumber++) {
		    	  
		        if (lineNumber == line) {
		        	
		          content = readbuffer.readLine();
		          
		        } else
		        	
		          readbuffer.readLine();
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
		
		
		
		return content;
	}
	public static boolean access(String[] list, String tocheck) {
		
		for(int x = 0; x<list.length; x++) {
			
			if(list[x].equals(tocheck)) {
				return true;
			}
			
		}
		
		
		
		
		
		return false;
	}
	public static void extendlog(String uname, String[] worlds) {
		
		String cnt = getContent(getLine("a.txt", uname))+", ";
		
		
		String wstring = "";
		
		for(int i = 0; i<worlds.length; i++) {
			
			wstring = wstring+worlds[i]+", ";
			
			
		}
		
		String finalstr = cnt + wstring;
		System.out.println(finalstr);
		
		/**
		
		String[] stripped = cnt.split(", ");
		
		String unamepw = stripped[0] + ", " + stripped[1]+", ";
		
		String finalstr = unamepw+wstring;
		System.out.println(finalstr);
		
		**/
		
		
		/**
		
		String pw = getPW(uname);
		System.out.println("pw: "+pw);
		String log = uname+", "+pw+", ";
		
		for(int i = 0; i<worlds.length; i++) {
			
			log = log+ worlds[i]+" ,";
			
		}
		
		System.out.println(log);
		**/
	}
	public static String getPW(String uname) {
		String pw = "";
		
		if(isRgst(uname)) {
			
			int line = getLine("a.txt", uname);
			String cnt = getContent(line);
			String[] list = cnt.split(", ");
			pw = list[1];
			
		}
		
		
		
		return pw;
	}
	
	public static int countLines() throws IOException {
		

		String filename = "b.txt";
		FileReader readfile = new FileReader(filename);
	    BufferedReader reader = new BufferedReader(readfile);
		StringBuffer stb = new StringBuffer("");
		
		int currentline = 0;
		while (reader.readLine() != null) {
			
			currentline=currentline+1;
			
		}
		
		
		return currentline;
	}
	public static void eraseLine(int eline) throws IOException {
		String filename = "b.txt";
		FileReader readfile = new FileReader(filename);
	    BufferedReader reader = new BufferedReader(readfile);
		StringBuffer stb = new StringBuffer("");
		FileWriter tempFile = new FileWriter("tempfile");
		
		
		int currentline = 0;
		String x = "";
		
		int linenumber = countLines()-1;
		
		int correctline = 0;
		
		if(linenumber >= 0) {
			
			
			
			String[] lines = new String[linenumber];
			
			
			while ((x = reader.readLine()) != null) {
				
				
				
				if(currentline != eline ){
					stb.append(x);
					lines[correctline] = x;
					correctline = correctline+1;
				}
				
				currentline=currentline+1;
				
			}
			
			for(int i = 0; i<lines.length; i++) {
				tempFile.write(lines[i]+"\n");
			}
			
			readfile.close();
			reader.close();
			
			FileWriter bfile = new FileWriter("b.txt");
			
			for(int a = 0; a<lines.length; a++) {
				bfile.write(lines[a]+"\n");
			}
			bfile.flush();
		}	
	}
	
}
