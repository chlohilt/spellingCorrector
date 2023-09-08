package spell;

import java.util.Set;

public class Trie implements ITrie {
  private Node root;
  private int wordCount;
  private int nodeCount;
  Set<String> words;

  @Override
  public void add(String word) {
    word.toLowerCase();
    root.
    for (int i=0; i < word.length(); ++i) {
      int index = word.charAt(i) – 'a';
    }
  }


  public void add_Helper() {

  }

  @Override
  public String toString() {
    StringBuilder currWord=new StringBuilder();
    StringBuilder output=new StringBuilder();
    toString_Helper(root, currWord, output);

    return output.toString();
  }

  private void toString_Helper(Node n, StringBuilder currWord, StringBuilder output) {
    if (n.getValue() > 0) {
      output.append(currWord.toString());
      output.append("\n");
    }

    for (int i=0; i < n.getChildren().length; ++i) {
      Node child=n.getChildren()[i];
      if (child != null) {
        char childLetter=(char) ('a' + i);
        currWord.append(childLetter);

        toString_Helper(child, currWord, output);

        currWord.deleteCharAt(currWord.length() - 1);
      }
    }
  }

  @Override
  public Node find(String word) {
    for (int i=0; i < word.length(); ++i) {
      char currChar = word.charAt(i);
    }
  }

  @Override
  public int hashCode() {

    return wordCount * nodeCount; // TODO: add index of each of the root node's non-null children

  }

  @Override
  public boolean equals(Object o) {
    // check if o is null -> return false or class is not this same
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    } else if (o == this) {
      return true;
    }
    // cast o to be a dictionary
    Trie t=(Trie) o;
    if (((Trie) o).getWordCount() != this.getWordCount() || ((Trie) o).getNodeCount() != this.getNodeCount()) {
      return false;
    }

    return equals_Helper(this.root, t.root);

  }

  //TODO: finish this
  private boolean equals_Helper(Node n1, Node n2) {
    // do n1 and n2 have the same count false if fals
    if (n1.getValue() != n2. getValue()) {
      return false;
    }
    // children in the same positions?
    for (int i=0; i < n1.getChildren().length; ++i) {

    }

    // recurse on the children and compare child subtrees
    return equals_Helper()
  }

  @Override
  public int getWordCount() {
    return wordCount;
  }

  @Override
  public int getNodeCount() {
    return nodeCount;
  }
}
