package org.example.tournament;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.example.game.Game;
import org.example.player.Player;

public class Tournament {
  private final Node tournamentTreeRoot;
  private final Game game;

  public Tournament(Game game, List<Player> participants) {
    this.tournamentTreeRoot = generateTournamentTree(participants);
    this.game = game;
  }

  private Node generateTournamentTree(List<Player> participants) {
    Queue<Node> nodesQueue = new LinkedList<>();

    for (Player participant : participants) {
      nodesQueue.add(new Node(participant));
    }

    while (nodesQueue.size() > 1) {
      int currentSize = nodesQueue.size();
      for (int i = 0; i < currentSize; i += 2) {
        Node leftNode = nodesQueue.poll();
        Node rightNode = nodesQueue.poll();

        Node matchNode = new Node(null);
        matchNode.setLeft(leftNode);
        matchNode.setRight(rightNode);

        nodesQueue.add(matchNode);
      }
    }

    return nodesQueue.poll();
  }

  public Player startTournament() {
    return playMatch(tournamentTreeRoot);
  }

  private Player playMatch(Node matchNode) {
    if (matchNode == null || matchNode.getParticipant() != null) {
      return matchNode != null ? matchNode.getParticipant() : null;
    }

    Player leftParticipant = playMatch(matchNode.getLeft());
    Player rightParticipant = playMatch(matchNode.getRight());

    if (leftParticipant == null && rightParticipant == null) {
      return null;
    } else if (leftParticipant == null) {
      return rightParticipant;
    } else if (rightParticipant == null) {
      return leftParticipant;
    } else {
      return game.startTournamentGame(leftParticipant, rightParticipant);
    }
  }
}
