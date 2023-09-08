package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

  private Trie dictionary=new Trie();

  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
    try {
      File myFile=new File(dictionaryFileName);
      var in=new Scanner(myFile);
      while (in.hasNext()) {
        var newString=in.nextLine();
        if (dictionary.find(newString) != null) {
          // TODO: add one to count
        } else {
          dictionary.add(newString);
        }
        ;
      }
    } catch (IOException e) {
      throw new IOException("Incorrect file name. Please try again. " + e);
    }
  }

  @Override
  public String suggestSimilarWord(String inputWord) {
    return null;
  }
}
