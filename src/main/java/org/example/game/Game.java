package org.example.game;

import org.example.player.Player;

public interface Game {
  void startSingleGame(String playerName);

  Player startTournamentGame(Player player1, Player player2);

  void playRound();

  Player determineRoundWinner(String player1Move, String player2Move);

  void updateScore(String roundWinner);

  boolean isGameOver(int totalWinNeeded);

  Player determineGameWinner();
}
