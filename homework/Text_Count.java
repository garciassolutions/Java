class Text_Count{
  public static void main(String args[]) throws  IOException, FileNotFoundException{
    List <int> word_count = new ArrayList<int>();
    File inputFile = null;
    Scanner words = null;
    String filename, line;
    BufferedReader br = null;
    try{
      br = new BufferedReader(new FileReader(filename));      
    }
    catch(FileNotFoundException x){
      x.printStackTrace();      
    }
    while((line=br.readline()) != null){
      for(String word : (line.replace("\\d+", " ")).split("\\s+")) System.out.println(word);
    }
  }
}
