package spell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trie implements ITrie {
  private Node root=new Node();
  List<String> words=new ArrayList<String>();
  private int wordCount=0;
  private int nodeCount=1;

  @Override
  public void add(String word) {
    word=word.toLowerCase();
    Node currentNode=root;

    for (int i=0; i < word.length(); ++i) {
      int index=word.charAt(i) - 'a';
      Node child=currentNode.getChildAt(index);

      if (child == null) {
        Node newNode=new Node();
        currentNode.addChild(newNode, index);
        currentNode=newNode;
        if (i != word.length() - 1) {
          nodeCount++;
        }
      } else {
        currentNode=child;
      }

      if (i == word.length() - 1 && child == null) {
        nodeCount++;
      }
    }
    // check to see if words is already added
    boolean found=false;
    for (String wordCheck : words) {
      if (wordCheck == word) {
        found=true;
        break;
      }
    }

    if (!found) {
      words.add(word);
    }
    currentNode.incrementValue();
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
    // check to see if words is there
    boolean found=false;
    for (String wordCheck : words) {
      if (wordCheck == word) {
        found=true;
        break;
      }
    }

    if (!found) {
      return null;
    }

    word=word.toLowerCase();
    Node currentNode=root;

    for (int i=0; i < word.length(); ++i) {
      int index=word.charAt(i) - 'a';
      Node child=currentNode.getChildAt(index);
      if (child != null) {
        currentNode=child;
      } else {
        return null;
      }
    }

    return currentNode;
  }

  @Override
  public int hashCode() {
    int indexFirstNonNullNode; //random number above 26 to initialize
    for (int i=0; i < root.getChildren().length; ++i) {
      if (root.getChildAt(i) != null) {
        indexFirstNonNullNode=i;
        return getWordCount() * nodeCount * indexFirstNonNullNode;
      }
    }
    return getWordCount() * nodeCount;
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

  private boolean equals_Helper(Node n1, Node n2) {
    // do n1 and n2 have the same count false if false
    if (n1.getValue() != n2.getValue()) {
      return false;
    }
    // children in the same positions?
    for (int i=0; i < n1.getChildren().length; ++i) {
      if ((n1.getChildAt(i) != null && n2.getChildAt(i) == null) || (n1.getChildAt(i) == null && n2.getChildAt(i) != null)) {
        return false;
      } else if (n1.getChildAt(i) != null && n2.getChildAt(i) != null) {
        if (!equals_Helper(n1.getChildAt(i), n2.getChildAt(i))) {
          return false;
        }  // recurse on the children and compare child subtrees
      }
    }
    return true;
  }

  @Override
  public int getWordCount() {
    return words.size();
  }

  @Override
  public int getNodeCount() {
    return nodeCount;
  }

}
