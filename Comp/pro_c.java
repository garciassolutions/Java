import java.util.*;

public class pro_c{
  public static String nouns[], verbs[], adjectives[]; // First of all these will be a number.
  public static void main(String args[]){
    Integer inputs = 0;
    Scanner input = new Scanner(System.in);
    inputs = Integer.parseInt(input.nextLine());
    while(inputs-- > 0){ // Forget a for loop.
      nouns = input.nextLine().split(" ");
      verbs = input.nextLine().split(" ");
      adjectives = input.nextLine().split(" ");
      String sentence[] = new String[nouns.length+verbs.length+adjectives.length-3];
      int i=0;
      for(;i<nouns.length-1;i++) sentence[i]=nouns[i+1];
      for(int x=1;x<verbs.length;x++) sentence[i++] = verbs[x];
      for(int x=1;x<adjectives.length;x++) sentence[i++] = adjectives[x];
      Arrays.sort(sentence);
//      for(String p : sentence) System.out.println(p);
      System.out.println((check_sentence(sentence) > 0)?"correct":"wrong");
    }
  }
  public static int check_sentence(String sentence[]){
    for(int i=0;i<sentence.length;){ // Same as a while loop, but declare a variable.
      for(int r=0;r<2;r++){
        int ret = noun_phrase(sentence, i);
        if(r!=1){
          if(ret == 2 && check_verb(sentence[i+2]) > 0) i+=3; // There was an adjective and a noun
          else if(ret == 1 && check_verb(sentence[i+1]) > 0) i+=2; // It was just a noun.
          else return 0; // It could have been either, but it doesn't have a verb.
        }
        else{
          if(ret == 2) i+=2; // There was an adjective and a noun
          else if(ret == 1) i+=1; // It was just a noun.
          else return 0; // It could have been either, but it doesn't have a verb.        
        }
      }
    }
    return 1;
  }
  public static int noun_phrase(String sentence[], int x){
    int ret = 0;
    for(int i=1;i<adjectives.length;i++)
      if(sentence[x].equals(adjectives[i])){
        ret++;
        i=adjectives.length;
        x++; // For the noun check below.
      }
    for(int i=1;i<nouns.length;i++)
      if(sentence[x].equals(nouns[i])){
        ret++;
        i=nouns.length;
      }
//    System.out.println("returning: " + ret);
    return ret;
  }
  public static int check_verb(String tmp){
    for(int i=1;i<verbs.length;i++)
      if(tmp.equals(verbs[i])) return 1;
    return 0;
  }
}
