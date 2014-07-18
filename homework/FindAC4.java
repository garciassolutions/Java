import java.util.*;
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

public class FindAC4{
  protected static final int N = 3;
  protected static final int MIN_AREA = 200;
  protected static final int MAX_AREA = 999;
  private static JFrame win;
  private static int index = 0;
  private static HashMap areaCodes = new HashMap();
  
  public static class Code{
    public String loc;
    public int code;
    public void Code(int x, String y){
      this.code = x;
      this.loc = y;
    }
  }
  
  public static void Write_File(String filename){
    ObjectOutputStream outputStream = null;
    try{
      outputStream = new ObjectOutputStream(new FileOutputStream(filename));
      Iterator i = areaCodes.entrySet().iterator();
      while(i.hasNext()){
        Map.Entry val = (Map.Entry)i.next();
        outputStream.writeObject(val);
      }
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
      Code obj;
      while(!(obj = (Code)inputStream.readObject()).equals(null)){
        areaCodes.put(obj.code, obj);
        index++;
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
          Code tmp = new Code();
          tmp.Code(code, input);
          areaCodes.put(code, tmp);
        }
        catch(NullPointerException e){
           e.printStackTrace();
        }
      }
      else{
        Code tmp = (Code)query(code);
        JOptionPane.showMessageDialog(win, tmp.loc + " " + tmp.code);
      }
    }
    return -1;
  }
  
  public static String queryLocation(int areaCode){
    return JOptionPane.showInputDialog("Area code " + areaCode + " was not found in the database.\nPlease enter the location.");
  }
  
  public static Object query(int ac){
    return areaCodes.get(ac);
  }

  public static void main(String args[]){
    try{
      Read_File("FindAC4.dat");
    }
    catch(ClassNotFoundException ex){
      ex.printStackTrace();
    }
    win = new JFrame();
    queryAreaCode();
    Write_File("FindAC4.dat");
    System.exit(0);
  }
}
