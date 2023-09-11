package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.google.common.collect.Sets;

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
          dictionary.find(newString).incrementValue();
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
      if (editDistance1.size() == 1 && dictionary.find(String.valueOf(itr)).getValue() >= 1) {
        return editDistance1.iterator().next();
      } else {
        // case that there are many edit dist 1
        if (mostSimilarWord(editDistance1) != null) {
          return mostSimilarWord(editDistance1);
        } else { // otherwise none of the edit dist of 1 are in the dictionary
          Set<String> editDistance2=null;
          while (itr.hasNext()) {
            Set<String> editDist2Set=generateSimilarWords(String.valueOf(itr));
            editDistance2.addAll(editDist2Set);
          }

          // REPEAT
          Iterator<String> itr2=editDistance2.iterator();
          if (editDistance2.size() == 1 && dictionary.find(String.valueOf(itr2)).getValue() >= 1) {
            return editDistance2.iterator().next();
          } else {
            // case that there are many edit dist 1
            if (mostSimilarWord(editDistance2) != null) {
              return mostSimilarWord(editDistance2);
            } else { // otherwise none of the edit dist of 2 are in the dictionary
              return null;
            }
          }
        }
        }
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

    //first get what has the highest incidence
    while (itr.hasNext()) {
      if (dictionary.find(String.valueOf(itr)).getValue() > highestFrequency) {
        highestFrequency=dictionary.find(String.valueOf(itr)).getValue();
      }
    }

    if (highestFrequency == 0) {
      return null;
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


