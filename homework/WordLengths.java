// Anthony J. Garcia
// 2o1o
// Homework one.

import java.util.*;
import java.io.*;
import javax.swing.*;

class WordLengths{
  public static void main(String args[]){
    ArrayList <Integer> wordcount = new ArrayList<Integer>();
    String filename = null;
    BufferedReader br = null;
    String line = null;
    int count = 0, shortest = 100, mean = 0, longest=0, tmp=0;
    while((filename = JOptionPane.showInputDialog("Enter the filename to parse")) == null);
    for(int i=0;i<100;i++) wordcount.add(0); // A little buffer.
    // Plus i cannot catch an exception, and then set the element.
    // Java doesn't care if i set the initialCapacity to anything.
    try{
      br = new BufferedReader(new FileReader(filename));
      while((line=br.readLine()) != null){
        line.replaceAll("'", ""); // Get rid of apostrophes.
        for(String i : line.split("[^a-zA-Z|\\w+\\-\\w+]")){ // Split by anything non alphabetic or hyphened word.
          try{
            if(i.length() > 0) wordcount.set(i.length(), wordcount.get(i.length())+1);
          }
          catch(IndexOutOfBoundsException p){
            // Originally no elements were set to 0, and the array size was 100.
            // So if you tried a array.get() on an empty element it would throw this exception.
            wordcount.set(i.length(), 1); // This would be nice if it worked...
          }
        }
      }
    }
    catch(FileNotFoundException x){
      x.printStackTrace();
      System.exit(1);
    }
    catch(IOException e){
      e.printStackTrace();
    }
    for(int i = 0;i<wordcount.size();i++){
      if((tmp=wordcount.get(i)) != 0){
        if(shortest > i) shortest = i;
        else if(longest < i) longest = i;
        mean+=tmp*i; // The total number of characters, times the amount they occur.
        count+=tmp; // How many words were this length.
      }
    }
    System.out.println("The shortest word is: " + shortest + " characters long.");
    System.out.println("The mean character count is: " + mean/count);
    System.out.println("The longest word is: " + longest + " characters long.");
  }
}
