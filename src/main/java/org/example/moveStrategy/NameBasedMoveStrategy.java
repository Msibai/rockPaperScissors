package org.example.moveStrategy;

import java.util.Random;
import org.example.player.Player;

public class NameBasedMoveStrategy implements PlayerMoveStrategy<Player> {
  @Override
  public String generateMove(Player opponentPlayer) {
    String playerName = opponentPlayer.getName();
    int hash = playerName.hashCode();
    int mappedValue = mapHashToRange(hash);

    Random random = new Random();
    int randomMove = random.nextInt(3);

    int finalMove = (mappedValue + randomMove) % 3;

    return chooseMove(finalMove);
  }

  private int mapHashToRange(int hash) {
    return Math.floorMod(hash, 3);
  }

  private String chooseMove(int value) {
    return switch (value) {
      case 0 -> "rock";
      case 1 -> "paper";
      case 2 -> "scissors";
      default -> "rock";
    };
  }
}
