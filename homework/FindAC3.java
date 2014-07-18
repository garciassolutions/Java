import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;  
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.EOFException;
import javax.swing.*;

public class FindAC2 implements Serializable{
  protected static final int N = 3;
  protected static final int MIN_AREA = 200;
  protected static final int MAX_AREA = 999;
  private static JFrame win;
  private static int index = 0;
  private static ArrayList <ACEntry> areaCodes[] = new ArrayList<ACEntry>(N);

  private class ACEntry implements Serializable{
    private int areacode;
    private String location;
    public void init(){
      this.areacode = 0;
      this.location = null;
    }
    public int get_ac(){
      return this.areacode;
    }
    public String get_loc(){
      return this.location;
    }
    public void set_loc(String loc){
      this.location = loc;
    }
    public void set_area(int area){
      this.areacode = area;
    }
    public void add(int ac, String loc){
      this.areacode = ac;
      this.location = loc;
    }
  }
  
  public static void Write_File(String filename){
    ObjectOutputStream outputStream = null;
    try{
      outputStream = new ObjectOutputStream(new FileOutputStream(filename));
      for(int x=0;x<N;x++) outputStream.writeObject(areaCodes.get(x));
    }
    catch(FileNotFoundException ex){
      ex.printStackTrace();
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
    try{
      if(outputStream != null){
        outputStream.flush();
        outputStream.close();
      }
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
  }
  
  public static boolean Read_File(String filename) throws ClassNotFoundException{
    ObjectInputStream inputStream = null;
    try{
      inputStream = new ObjectInputStream(new FileInputStream(filename));
      for(int x=0;x<N;x++){
        areaCodes[x] = (ACEntry)(inputStream.readObject());
        if(areaCodes[x].get_loc() != null) index++;
      }
    }
    catch(FileNotFoundException ex){
      ex.printStackTrace();
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
    catch(ClassNotFoundException ex){
      ex.printStackTrace();
    }
    try{
      if(inputStream != null) inputStream.close();
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
    return index>0?true:false;
  }
    
  public static void main(String args[]){
    FindAC2 idono = new FindAC2();
    for(int x=0;x<N;x++){
      areaCodes[x] = idono.new ACEntry();
      areaCodes[x].init();
    }
    try{
      Read_File("FindAC.dat");
    }
    catch(ClassNotFoundException ex){
      ex.printStackTrace();
    }
    win = new JFrame();
    queryAreaCode();
    Write_File("FindAC.dat");
    System.exit(0);
  }
  
  private static boolean isValid(int areaCode){
    return (areaCode <= MAX_AREA && areaCode >= MIN_AREA);
  }
  
  private static int inputAreaCode() throws NumberFormatException{
    String input = "Anthony J. Garcia";
    int result = 0;
    while(result == 0){ // && !input.matches("^[" + MIN_AREA + "-" + MAX_AREA + "]$")){
      input = JOptionPane.showInputDialog("Please enter a area code.");
      if(input == null || input.length() == 0){
        result = -1;
        break;
      }
      try{
        if(!isValid(result=Integer.parseInt(input))) result ^= result;
        else break;
      }
      catch(NumberFormatException e){} // The user doesn't need to know about this. And we don't need to do anything special.
      JOptionPane.showMessageDialog(win, "Correct values are " + MIN_AREA + " to " + MAX_AREA + ". Please try again.");
    }
    return result;
  }
  
  public static int queryAreaCode(){
    int code;
    while((code=inputAreaCode()) > 0){
      if(query(code) == null){
        String input = null;
        while(input == null || input.length() == 0) input = queryLocation(code);
        try{
          areaCodes[index++].add(code, input);
        }
        catch(NullPointerException e){
           e.printStackTrace();
        }
      }
      else{
        JOptionPane.showMessageDialog(win, query(code));
      }
    }
    return -1;
  }
  
  public static String queryLocation(int areaCode){
    return JOptionPane.showInputDialog("Area code " + areaCode + " was not found in the database.\nPlease enter the location.");
  }
  
  public static String query(int areaCode){
    int x = 0;
    for(;x<index;x++)
      if(areaCodes[x].get_ac() == areaCode) return areaCodes[x].get_loc();
    return null;
  }
}
