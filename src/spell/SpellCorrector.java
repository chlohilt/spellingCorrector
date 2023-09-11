package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

  private Trie dictionary=new Trie();
  char[] alphabet="abcdefghijklmnopqrstuvwxyz".toCharArray();

  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
    try {
      File myFile=new File(dictionaryFileName);
      var in=new Scanner(myFile);
      while (in.hasNext()) {
        var newString=in.nextLine();
        if (dictionary.find(newString) == null) {
          dictionary.add(newString);
        } else {
          // add one count to the Node at the end
        }
      }
    } catch (IOException e) {
      throw new IOException("Incorrect file name. Please try again. " + e);
    }
  }

  @Override
  public String suggestSimilarWord(String inputWord) {
    if (dictionary.find(inputWord) != null) {
      return inputWord;
    } else {
      // edit distance of 1
      Set<String> editDistance1=generateSimilarWords(inputWord);
      Iterator<String> itr=editDistance1.iterator();
      // case that there is only one in edit dist of 1
      if (editDistance1.size() == 1) {
        return editDistance1.iterator().next();
      } else if (editDistance1.size() == 0) {
        // case that there are no edit dist of 1
//        Set<String> editDistance2=null;
//        while (itr.hasNext()) {
//          Set<String> setToUnion
//        }
      } else {
        // case that there are many edit dist 1
        return mostSimilarWord(editDistance1);
      }
    }
    return null;
  }

  public Set<String> generateSimilarWords(String inputWord) {
    Set<String> possibleWords=null;
    for (int i=0; i < inputWord.length(); ++i) {
      possibleWords.add(inputWord.substring(0, i) + inputWord.substring(i + 1)); // take out one character
      possibleWords.add(inputWord.substring(0, i) + inputWord.charAt(i + 1) + inputWord.charAt(i) + inputWord.substring(i + 1)); // switching the characters
      // loop through alphabet for alteration and insertion distance
      for (int j=0; j < alphabet.length; ++j) {
        possibleWords.add(inputWord.substring(0, i) + alphabet[j] + inputWord.substring(i)); // insertion distance
        possibleWords.add(inputWord.substring(0, i - 1) + alphabet[j] + inputWord.substring(i + 1)); // alteration distance
      }
    }
    return possibleWords;
  }

  public String mostSimilarWord(Set<String> possibleWords) {
    Iterator<String> itr=possibleWords.iterator();
    int highestFrequency=0;
    List<String> multiplePossible=new ArrayList<>();

    //first get what has highest incidence
    while (itr.hasNext()) {
      if (dictionary.find(String.valueOf(itr)).getValue() > highestFrequency) {
        highestFrequency=dictionary.find(String.valueOf(itr)).getValue();
      }
    }

    // now get same ones if there are some
    while (itr.hasNext()) {
      if (dictionary.find(String.valueOf(itr)).getValue() == highestFrequency) {
        multiplePossible.add(String.valueOf(itr));
      }
    }

    // return if there's just one or alphabetically first
    if (multiplePossible.size() >= 1) {
      Collections.sort(multiplePossible);
      return multiplePossible.get(0);
    }

    return null;
  }
}


