import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class FindFile{
	static void Problem(String ERR){
		System.err.println(ERR);
		System.err.println("Usage is: java FindFile.java \"C:\\Example\\Folder\\\" \"File To Find\"");
		System.exit(1);
	}
	static void Search(File DIR, String to_find){
		File lst[] = DIR.listFiles();
	  	for(int i=0;i<lst.length;i++){
	  		if(lst[i].isDirectory()) Search(lst[i], to_find);
	  		else if(lst[i].getName().equals(to_find)){
	  			System.out.println(lst[i].getPath());
	  		}
	  	}
	  	// System.out.println(lst[0]); // Returns an array of files with name "to_find" in the current directories.
	}
	public static void main(String args[]){
		if(args.length != 2) Problem("Invalid command line arguements.");
		File IN = new File(args[0]);
	 	if(!IN.isDirectory() && !IN.exists()) Problem(args[0] + " is not a valid directory.");
	 	Search(IN, args[1]);
	}
}
