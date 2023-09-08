package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

  private Map<String, Integer> dictionary=new HashMap<>();

  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
    try {
      File myFile=new File(dictionaryFileName);
      var in=new Scanner(myFile);
      while (in.hasNext()) {
        var newString=in.nextLine();
        dictionary.merge(newString, 1, Integer::sum);
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
