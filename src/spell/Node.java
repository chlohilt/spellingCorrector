package spell;

import java.lang.reflect.Array;

public class Node implements INode {

  private int count=0;
  private Node[] children=new Node[26];

  @Override
  public int getValue() {
    return count;
  }

  @Override
  public void incrementValue() {
    count+=1;
  }

  @Override
  public Node[] getChildren() {
    return this.children;
  }
}
