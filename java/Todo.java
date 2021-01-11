import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
public class Todo {
	private static File todo,done;
	public static void main(String args[]){
		try {
			todo=new File("todo.txt");
			todo.createNewFile();
			done=new File("done.txt");
			done.createNewFile();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		done=new File("done.txt");
		if(args.length==0) {
			help();
		}
		else {
			switch(args[0]) {			
			case "help":help();
			break;
			case "ls":listAll();
			break;
			case "add":
				if(args.length>1)addTodo(args[1]);
				else System.out.println("Error: Missing todo string. Nothing added!");
			break;
			case "del":
				if(args.length>1) del(args[1]);
				else System.out.println("Error: Missing NUMBER for deleting todo.");
			break;
			case "done":
				if(args.length>1) done(args[1]);
				else System.out.println("Error: Missing NUMBER for marking todo as done.");
			break;
			case "report": report();
			break;
			default:
				System.out.
				println("Option Not Available. Please use \"./todo help\" for Usage Information");
				
			}
			
		}
	}
	private static void help() {
		String help="Usage :-\n"
				+ "$ ./todo add \"todo item\"  # Add a new todo\n"
				+ "$ ./todo ls               # Show remaining todos\n"
				+ "$ ./todo del NUMBER       # Delete a todo\n"
				+ "$ ./todo done NUMBER      # Complete a todo\n"
				+ "$ ./todo help             # Show usage\n"
				+ "$ ./todo report           # Statistics";
		System.out.println(help);
	}
	private static void listAll() {
		try {
		String[] s=readFile(todo).split("\n");
		if(s.length==0)
			System.out.println("There are no pending todos!");
		for(int i=0;i<s.length;i++) {
			System.out.println(new String(("["+(s.length-i)+"] "+s[i]).getBytes("UTF-8"),"UTF-8"));
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		}
	private static void addTodo(String s) {
		try {
			String content=readFile(todo);
			content=s+"\n"+content;
			writeFile(content,"todo.txt");
			System.out.println(new String(("Added todo: \""+s+"\"").getBytes("UTF-8"),"UTF-8"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	private static void del(String s) {
		Integer n=Integer.valueOf(s);
		try {
		String[] content=readFile(todo).split("\n");
		String updatedString="";
		if(content.length<n||n==0) {
			System.out.println("Error: todo #"+n+" does not exist. Nothing deleted.");
		}
		else {
			for(int i=0;i<content.length;i++) {
				if(n==content.length-i) {
					continue;
				}
				else {
					updatedString+=content[i]+"\n";
				}
			}
			writeFile(updatedString,"todo.txt");
			System.out.println("Deleted todo #"+n);
		}
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void done(String s) {
		Integer n=Integer.valueOf(s);
		try {
			String[] content=readFile(todo).split("\n");
			String updatedString="";
			if(content.length<n||n==0) {
				System.out.println("Error: todo #"+n+" does not exist.");
			}
			else {
				for(int i=0;i<content.length;i++) {
					if(n==content.length-i) {
						String prevContent=readFile(done);
						String done="x "+
							(new SimpleDateFormat("yyyy-MM-dd")).format(new Date())+" "+
							content[i];
						writeFile(done+"\n"+prevContent,"done.txt");
					}
					else {
						updatedString+=content[i]+"\n";
					}
				}
				writeFile(updatedString,"todo.txt");
				System.out.println(new String(("Marked todo #"+n+" as done.").getBytes("UTF-8"),"UTF-8"));
			}
			
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	private static void report() {
		try {
		int pending=readFile(todo).split("\n").length;
		int completed=readFile(done).split("\n").length;
		System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(new Date())+
							" Pending : "+pending+
							" Completed : "+completed);
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static String readFile(File filename)throws Exception {
		String data="";
		      Scanner myReader = new Scanner(filename);
		      while (myReader.hasNextLine()) {
		        data += myReader.nextLine()+"\n";
		      }
		      myReader.close();
		return data;
	}
	private static void writeFile(String s,String fileName) throws Exception {		
		FileWriter writer=new FileWriter(fileName);
		writer.write(s+"\n");
		writer.close();		
	}
}

