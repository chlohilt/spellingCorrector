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
        var newString=in.next();
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
    inputWord=inputWord.toLowerCase();
    if (dictionary.find(inputWord) != null) {
      return inputWord;
    } else {
      // edit distance of 1
      Set<String> editDistance1=generateSimilarWords(inputWord);
      Iterator<String> itr=editDistance1.iterator();

      String itrNext=null;
      if (editDistance1.size() > 0) {
        itrNext=itr.next();

      }
      // case that there is only one in edit dist of 1
      if (editDistance1.size() == 1 && dictionary.find(itrNext).getValue() >= 1) {
        return itrNext;
      } else {
        // case that there are many edit dist 1
        if (mostSimilarWord(editDistance1) != null) {
          return mostSimilarWord(editDistance1);
        } else { // otherwise none of the edit dist of 1 are in the dictionary
          Set<String> editDistance2=new HashSet<>();
          while (itr.hasNext()) {
            Set<String> editDist2Set=generateSimilarWords(itrNext);
            if (editDist2Set.size() != 0) {
              editDistance2.addAll(editDist2Set);
            }
            itrNext=itr.next();
          }

          // REPEAT
          Iterator<String> itr2=editDistance2.iterator();
          String itr2Find=null;
          if (editDistance1.size() > 0) {
            itr2Find=itr2.next();
          }
          if (editDistance2.size() == 1 && dictionary.find(itr2Find).getValue() >= 1) {
            return itr2Find;
          } else {
            // case that there are many edit dist 2
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

  public Set<String> generateSimilarWords(String inputWord) {
    Set<String> possibleWords=new HashSet<>();
    for (int i=0; i < inputWord.length(); ++i) {
      takeOutOneChar(possibleWords, inputWord, i);
      switchingChars(possibleWords, inputWord, i);

      // loop through alphabet for alteration and insertion distance
      for (int j=0; j < alphabet.length; ++j) {
        insertion(possibleWords, inputWord, i, j);
        alteration(possibleWords, inputWord, i, j);
      }
    }
    return possibleWords;
  }

  public void insertion(Set<String> possibleWords, String inputWord, int i, int j) {
    possibleWords.add(inputWord.substring(0, i) + alphabet[j] + inputWord.substring(i)); // insertion distance
    if (i + 1 == inputWord.length()) {
      possibleWords.add(inputWord + alphabet[j]);
    }
  }

  public void alteration(Set<String> possibleWords, String inputWord, int i, int j) {
    if (i + 1 < inputWord.length()) {
      possibleWords.add(inputWord.substring(0, i) + alphabet[j] + inputWord.substring(i + 1)); // alteration distance
    } else {
      possibleWords.add(inputWord.substring(0, i) + alphabet[j]);
    }
  }

  public void takeOutOneChar(Set<String> possibleWords, String inputWord, int i) {
    if (i + 1 < inputWord.length()) {
      possibleWords.add(inputWord.substring(0, i) + inputWord.substring(i + 1)); // take out one character
    } else {
      possibleWords.add(inputWord.substring(0, i));
    }
  }

  public void switchingChars(Set<String> possibleWords, String inputWord, int i) {
    if (i + 2 < inputWord.length()) {
      possibleWords.add(inputWord.substring(0, i) + inputWord.charAt(i + 1) + inputWord.charAt(i) + inputWord.substring(i + 2)); // switching the characters
    } else if (i + 1 < inputWord.length()) {
      possibleWords.add(inputWord.substring(0, i) + inputWord.charAt(i + 1) + inputWord.charAt(i)); // switching the characters
    } else {
      possibleWords.add(String.valueOf(inputWord.charAt(i) + inputWord.charAt(0)));
    }
  }

  public String mostSimilarWord(Set<String> possibleWords) {
    Iterator<String> itr=possibleWords.iterator();
    int highestFrequency=0;
    List<String> multiplePossible=new ArrayList<>();

    //first get what has the highest incidence
    while (itr.hasNext()) {
      String lookFor=itr.next();
      Node find=dictionary.find(lookFor);
      if (find != null) {
        if (find.getValue() > highestFrequency) {
          highestFrequency=find.getValue();
        }
      }
    }

    if (highestFrequency == 0) {
      return null;
    }

    Iterator<String> itr2=possibleWords.iterator();
    // now get same ones if there are some
    while (itr2.hasNext()) {
      String itrString=itr2.next();
      Node find=dictionary.find(itrString);
      if (find != null) {
        if (find.getValue() == highestFrequency) {
          multiplePossible.add(itrString);
        }
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


