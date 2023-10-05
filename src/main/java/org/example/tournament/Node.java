package org.example.tournament;

import org.example.player.Player;

public class Node {
  private final Player participant;
  private Node left;
  private Node right;

  public Node(Player participant) {
    this.participant = participant;
    this.left = null;
    this.right = null;
  }

  public Player getParticipant() {
    return participant;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }
}
