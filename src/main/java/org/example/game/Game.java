package org.example.game;

import org.example.player.Player;

public interface Game {
    void startSingleGame();

    Player startTournamentGame(Player player1, Player player2);

    void playRound();

    Player determineRoundWinner();
    void updateScore();

    boolean isGameOver();

    Player determineGameWinner();
}
