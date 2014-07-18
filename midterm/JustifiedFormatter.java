import java.io.*;

class JustifiedFormatter extends Formatter{
	public static void main(String args[]){
			Formatter fmt = new Formatter("DOI.txt", 25);
	}
	public static void outputLine(StringBuilder outLine){
		if(outLine.length() < columnWidth){
		
		}
		System.out.printf("%s\n", outLine);
		outLine.setLength(0);
	}
	public static int countSpaces(StringBuilder outLine){
		return 1;
	}
	public static void addSpaces(StringBuilder outLine){
		
	}
}
