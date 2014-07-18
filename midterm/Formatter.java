// Java midterm :: 11.26.2oo9
// Written by Anthony J. Garcia

import java.io.*;
import java.util.Scanner;

class Formatter{
	protected static final int MIN_WIDTH = 20;
	protected static final int MAX_WIDTH = 1000;
	
	protected static int columnWidth;
	protected static StringBuilder outputLine = new StringBuilder();
	
	public static void main(String args[]) throws NumberFormatException, IOException, IllegalArgumentException, FileNotFoundException{
		if(args.length != 2) usage(1);
		try{
			Integer.parseInt(args[1]);
		}
		catch(NumberFormatException x){
			usage(2);
		}
		try{
			setColumnWidth(Integer.parseInt(args[1]));
		}
		catch(IllegalArgumentException x){
			System.err.println(x);
			System.exit(1);
		}
		File inputFile = null;
		try{
			inputFile = new File(args[0]);
		}
		catch(NullPointerException x){
			usage(3);	
		}
		try{
			formatFile(inputFile);
		}
		catch(FileNotFoundException x){
			usage(3);
		}		
	//		inputFile.close(); // What language has a File class that doesn't have a close method? Take a guess, its Java!
	}
	public static void setColumnWidth(int x) throws IllegalArgumentException{
		if(!(x <= MAX_WIDTH && x >= MIN_WIDTH)) throw new IllegalArgumentException("Width must be between " + MIN_WIDTH + " and " + MAX_WIDTH);
		columnWidth = x;
	}
	public static void formatFile(File IN) throws FileNotFoundException{ // If its not a file we would have gotten the exception before calling this method.
		Scanner input = new Scanner(IN); // If you're going to just use this method make sure to catch the exception!
		String s;
		Scanner words = null;
		while(input.hasNext()){ // Make sure we can read at least one byte in.
			s = input.nextLine(); //  Read in a line.
			if(s.length() == 0){ // We read in a newline.
				flushLine(outputLine); // Flush...
				System.out.println("");
			}
			else{
				words = new Scanner(s).useDelimiter("\\s"); // Seperate the words by a space.
				formatLine(words); // Format our words.
			}
		}
		flushLine(outputLine);
		input.close();
		words.close();
	}
	public static void flushLine(StringBuilder outLine){
		System.out.printf("%s\n", outLine);
		outLine.setLength(0);
	}
	public static void outputLine(StringBuilder outLine){
		System.out.printf("%s\n", outLine);
	}
	public static void clearLine(StringBuilder outLine){
		outLine.setLength(0);
	}
	public static void formatLine(Scanner line){
		String s;
		while(line.hasNext()){
			s=line.next();
			if((outputLine.length() + s.length()) <= columnWidth){
				outputLine.append(s);
				outputLine.append(" ");
			}
			else{
				//flushLine(outputLine); // There's no reason for the two methods this does them both...
				outputLine(outputLine);
				clearLine(outputLine);
				outputLine.append(s);
				outputLine.append(" ");
			}
		}	
	}
	public static void usage(int x){
		switch(x){
			case 1: System.err.println("   (!) You must pass two arguments.");
			break;
			case 2: System.err.println("   (!) Width length must be a whole number.");
			break;
			case 3: System.err.println("   (!) Filename doesn't exist! Try again with a valid filename.");
		}
		System.err.println("   (!) Usage is: java Formatter [filename] [width length]");
		System.exit(x);
	}
}
