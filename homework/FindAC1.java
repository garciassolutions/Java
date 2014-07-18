import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;  
import javax.swing.*;

public class FindAC1{
  protected static final int N = 1;
  protected static final int MIN_AREA = 200;
  protected static final int MAX_AREA = 999;
  private static JFrame win;
  private static int index = 0;
  private static ACEntry areaCodes[] = new ACEntry[N];
  

  private class ACEntry{
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

  public static void main(String args[]){
    FindAC1 idono = new FindAC1();
    for(int x=0;x<N;x++){
      areaCodes[x] = idono.new ACEntry();
      areaCodes[x].init();
    }
    win = new JFrame();
    queryAreaCode();
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
