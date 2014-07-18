import java.util.*;

class pro_a{
  public static void main(String args[]){
    Integer wc, b_wc;
    Scanner input = new Scanner(System.in);
    wc = Integer.parseInt(input.nextLine());
    String words[] = new String[wc];
    int p = 0;
//    for(int i=0;i<wc;i++) words[i] = input.next();
    for(String x : input.nextLine().split(" ")) words[p++] = x;
    b_wc = Integer.parseInt(input.nextLine());
    String b_words[] = new String[b_wc];
    for(int i=0;i<b_wc;i++){
      b_words[i] = input.nextLine();
      b_words[i] = b_words[i].replaceAll(" ", "");
      b_words[i] = b_words[i].replaceAll("_", ".");
    }
    for(int i=0;i<b_words.length;i++){
      int matches = 0;
      for(int j=0;j<words.length;j++)
        if(words[j].matches(b_words[i])) matches++;
      System.out.println(matches);
    }
  }
}
