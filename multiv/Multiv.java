package multiv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Multiv extends JavaPlugin{
	
	
	
	@Override
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player p = (Player) sender;
		
		
		
		/** Teleport Command    **/
		if(cmd.getName().equalsIgnoreCase("teleport")) {
			
			if(args.length == 1) {
				
				
				String worldname = args[0];
				
				p.sendMessage(worldname);
				
				World world = Bukkit.getWorld(worldname);
				
				
				/** Teleport Player in the given World   **/
				if(world != null) {
					/** is world existing? **/
					
					if(isRgst(p.getName())) {
						/** is player registered? **/
						
						if(access(getWorlds(p.getName()), worldname)){
							/** can player access the world? **/
							
							p.teleport(world.getSpawnLocation());
							p.sendMessage("You have been teleported!");
							
						}
						else {
							p.sendMessage("You dont have the Permission to join this World!");
						}
						
					}
					
				}
				else if (world == null){
					p.sendMessage("This World doesnt exist");
				}
				
			}
			else{
				p.sendMessage("Error");
			}
		}
		
		
		/** Create Commad  **/
		
		else if(cmd.getName().equalsIgnoreCase("create")){
			
			String worldname = args[0];
			
			p.sendMessage("Creating World " + worldname + "... ");
			
			
			WorldCreator worldCreator = new WorldCreator(worldname);
			
			Bukkit.getServer().createWorld(worldCreator);
			
			//Bukkit.createWorld((WorldCreator) WorldCreator.name(worldname).environment(Environment.NORMAL).type(WorldType.NORMAL).createWorld());
			
			p.sendMessage("World " + worldname + " has been created!");
		}
		
		/** register command   **/
		
		else if(cmd.getName().equalsIgnoreCase("register")){
			
			if(args.length == 1) {
				
				String uname = p.getName();
				
				String pword = args[0];
				
				String[] worlds = {""};
				
				try {
					logtoFile("login.txt", uname, pword, worlds);
					p.sendMessage("Registration succesfull");
				} catch (IOException e) {e.printStackTrace();}
					
			}
			
		}
		
		/** permission add command **/
		
		else if (cmd.getName().equalsIgnoreCase("allow")) {
			
			
			if(args.length == 2) {
				String user = args [0];
				String world = args[1];
				
				addWorld(world, user);
				
				/**
				if(isRgst(user)) {
					
					
					String pw = getPW(user); //getPW(user);
					
					int line = getLine("login.txt", user);
					
					String[] worlds1 = getWorlds(user);
					System.out.println("worlds1: "+Arrays.deepToString(worlds1));
					String[] worlds = new String[worlds1.length+1];
					
					for(int i = 0; i<=worlds.length-1;i++) {
						
						try {
							worlds[i] = worlds1[i];
						}catch(ArrayIndexOutOfBoundsException e) {System.out.println("error in allow");worlds[i]=world;}
						
					}
					p.sendMessage(Arrays.deepToString(worlds));
					String logline = user+","+pw+",";
					for(int a = 0; a<worlds.length;a++) {
						
						logline = logline+worlds[a]+",";
						logline = logline.strip();
					}
					
					try {
						eraseLine(line);
						extendwrite("login.txt", logline+"\n");
					} catch (IOException e) {
						p.sendMessage("Error while trying to erase line");
						e.printStackTrace();
					}
					
				}**/
				
				
			}
					
					
				}
				
		
		return true;
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
	}
	public static void extendwrite(String filename, String msg) throws IOException {
		
		String towrite = read(filename) + msg;
		
		FileWriter fw = new FileWriter(filename);
		fw.write(towrite);
		fw.close();
		System.out.println("extendwrite succes");
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
		
		String towrite = read(filename); // +"\n";
		
		FileWriter fw = new FileWriter(filename);
		
		String line = name+","+pw+",";
		for(int x = 0; x< arg.length; x++) {
			
			if(arg[x] == null) {
				System.out.println("arg[x] ist null");
			}
			
			
			
			if(arg[x] != null) {
				
				
				line = line + arg[x]; //+",";
				
			}
			
			
		}
		
		towrite = towrite + line;
		
		fw.write(towrite);
		fw.close();
		
		
	}
	public static boolean isRgst(String uname) {
		
		if(getLine("login.txt", uname) == -1) {
			
			return false;
			
		}
		
		return true;
	}
	public static String[] getWorlds(String uname) {
		
		
		String content;
		String filename = "login.txt";
		
		int line = getLine(filename, uname);
		
		content = getContent(line);
		content = content.strip();
		System.out.println("content in gw:"+ content);
		
		String[] contentlist = content.split(",");
		//System.out.println(Arrays.deepToString(contentlist));
		String[] worlds = new String[contentlist.length-2];
		
		for(int i = 2; i<contentlist.length; i++) {
			//System.out.println("test");
			worlds[i-2] = contentlist[i];
			System.out.println("in getworlds: "+worlds[i-2]);
			
		}
		
		return worlds;
	}
	public static String getContent(int line) {
		String content ="";
		
		int lineNumber = 0;
		 
			 
			FileReader readfile;
			try {
				readfile = new FileReader("login.txt");
				BufferedReader readbuffer = new BufferedReader(readfile);
			    String x;
			      
			    while((x = readbuffer.readLine()) != null) {
			    	if (lineNumber == line) {	  
			    		content = x;
			    	}
			    	  
			    	lineNumber = lineNumber+1;
			    }
			} catch (IOException e) {
				System.out.println("Error in der getContent try!");
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
		
		String cnt = getContent(getLine("login.txt", uname))+",";
		
		
		String wstring = "";
		
		for(int i = 0; i<worlds.length; i++) {
			
			wstring = wstring+worlds[i]+",";
			
			
		}
		
		String finalstr = cnt + wstring;
		System.out.println("in extendlog: "+finalstr);
		
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
			
			int line = getLine("login.txt", uname);
			String cnt = getContent(line);
			String[] list = cnt.split(",");
			if(list.length >= 2) {
				pw = list[1];
			}
			else {
				System.out.println("list too short in getPW");
			}
			
		}
		
		
		
		return pw;
	}
	public static int countLines() throws IOException {
		

		String filename = "login.txt";
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
		String filename = "login.txt";
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
			
			FileWriter bfile = new FileWriter(filename);
			
			for(int a = 0; a<lines.length; a++) {
				bfile.write(lines[a]+"\n");
			}
			bfile.flush();
		}	
	}
	public static void addWorld(String world, String uname) {
		
		if(isRgst(uname)) {
			
			
			String pw = getPW(uname);
			
			int line = getLine("login.txt", uname);
			
			String[] worlds1 = getWorlds(uname);
			System.out.println("worlds1: "+Arrays.deepToString(worlds1));
			String[] worlds = new String[worlds1.length+1];
			
			for(int i = 0; i<=worlds.length-1;i++) {
				
				try {
					worlds[i] = worlds1[i];
				}catch(ArrayIndexOutOfBoundsException e) {System.out.println("error in allow");worlds[i]=world;}
				
			}
			String logline = uname+","+pw+",";
			for(int a = 0; a<worlds.length;a++) {
				
				logline = logline+worlds[a]+",";
				logline = logline.strip();
			}
			
			try {
				eraseLine(line);
				extendwrite("login.txt", logline+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}
