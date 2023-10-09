package org.example.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.example.moveStrategy.HumanPlayerMoveStrategy;
import org.example.moveStrategy.NameBasedMoveStrategy;
import org.example.moveStrategy.RandomMoveStrategy;
import org.example.moveStrategy.TimeBasedMoveStrategy;
import org.example.player.ComputerPlayerFactory;
import org.example.player.HumanPlayer;
import org.example.player.Player;
import org.example.statistics.GameStatistics;
import org.example.statistics.RoundHistory;

public class RPSgame implements Game {
  private final List<RoundHistory> roundHistoryList = new ArrayList<>();
  private final GameStatistics statistics;
  private int selectedComputerPlayerType;
  private Player player1;
  private Player player2;
  private int player1Score;
  private int player2Score;
  private int totalWinNeeded;

  public RPSgame(GameStatistics statistics, int selectedComputerPlayerType) {
    this.statistics = statistics;
    this.selectedComputerPlayerType = selectedComputerPlayerType;
  }

  public RPSgame(int totalWinNeeded, GameStatistics statistics) {
    this.statistics = statistics;
    this.totalWinNeeded = totalWinNeeded;
  }

  public static Player generateComputerPlayer(int selectedComputerPlayerType) {
    ComputerPlayerFactory factory = new ComputerPlayerFactory();
    int random = new Random().nextInt(100);
    return switch (selectedComputerPlayerType) {
      case 0 -> factory.createRandomComputerPlayer(
          "Random Beast " + random, new RandomMoveStrategy());
      case 1 -> factory.createTimeBasedComputerPlayer(
          "Time Master " + random, new TimeBasedMoveStrategy());
      case 2 -> factory.createNameBasedComputerPlayer(
          "Tactics Master " + random, new NameBasedMoveStrategy());
      default -> throw new IllegalStateException("Unexpected value: " + selectedComputerPlayerType);
    };
  }

  public static int getDesiredWinCount() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("Enter desired number of Wins to declare a Winner: ");
      String input = scanner.nextLine().trim();

      if (input.isEmpty()) {
        System.out.println("Please enter a valid integer.");
        continue;
      }

      try {
        int desiredWinCount = Integer.parseInt(input);

        if (desiredWinCount <= 0) {
          System.out.println("Please enter a positive integer.");
        } else {
          return desiredWinCount;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid integer.");
      }
    }
  }

  @Override
  public void startSingleGame(String playerName) {
    player1 = new HumanPlayer(playerName, new HumanPlayerMoveStrategy());
    player2 = generateComputerPlayer(selectedComputerPlayerType);

    totalWinNeeded = getDesiredWinCount();

    while (!isGameOver(totalWinNeeded)) {
      playRound();
      displayCurrentScores();
    }
    Player matchWinner = determineGameWinner();
    String matchWinnerName = matchWinner.getName();
    displayGameResult(matchWinnerName);
    statistics.updateStatistics(matchWinner, player2);
    displayRoundHistory();
  }

  @Override
  public Player startTournamentGame(Player player1, Player player2) {
    this.player1 = player1;
    this.player2 = player2;

    while (!isGameOver(totalWinNeeded)) {
      playRound();
      displayCurrentScores();
    }
    Player matchWinner = determineGameWinner();
    String matchWinnerName = matchWinner.getName();
    displayGameResult(matchWinnerName);
    return matchWinner;
  }

  @Override
  public void playRound() {
    Player winner = null;

    while (winner == null) {
      String player1Move = player1.makeMove(player2);
      String player2Move = player2.makeMove(player1);
      winner = determineRoundWinner(player1Move, player2Move);

      if (winner != null) {
        String resultForOneRound = winner.getName();
        updateScore(resultForOneRound);

        System.out.println(player1.getName() + " played: " + player1Move);
        System.out.println(player2.getName() + " played: " + player2Move);
        System.out.println("Result: " + resultForOneRound + "\n");
      }
      roundHistoryList.add(
          new RoundHistory(
              player1.getName(),
              player1Move,
              player2.getName(),
              player2Move,
              player1Score,
              player2Score));
    }
  }

  @Override
  public Player determineRoundWinner(String player1Move, String player2Move) {
    if (player1Move.equals(player2Move)) {
      System.out.println("Tie! Let's play another round.");
      return null;
    } else if ((player1Move.equals("rock") && player2Move.equals("scissors"))
        || (player1Move.equals("paper") && player2Move.equals("rock"))
        || (player1Move.equals("scissors") && player2Move.equals("paper"))) {
      System.out.println(player1.getName() + " wins!");
      return player1;
    } else {
      System.out.println(player2.getName() + " wins!");
      return player2;
    }
  }

  @Override
  public void updateScore(String roundWinner) {
    if (roundWinner.equals(player1.getName())) {
      player1Score++;
    } else if (roundWinner.equals(player2.getName())) {
      player2Score++;
    }
  }

  @Override
  public boolean isGameOver(int totalWinNeeded) {
    return player1Score == totalWinNeeded || player2Score == totalWinNeeded;
  }

  @Override
  public Player determineGameWinner() {
    if (player1Score > player2Score) {
      player1Score = 0;
      player2Score = 0;
      return player1;
    } else {
      player1Score = 0;
      player2Score = 0;
      return player2;
    }
  }

  private void displayCurrentScores() {
    System.out.println(
        "Current Score - "
            + player1.getName().toUpperCase()
            + ": "
            + player1Score
            + "     "
            + player2.getName().toUpperCase()
            + ": "
            + player2Score
            + "\n");
  }

  private void displayRoundHistory() {
    System.out.println("\n ROUND HISTORY");
    roundHistoryList.forEach(
        round ->
            System.out.println(
                round.getHumanPlayerName()
                    + ": "
                    + round.getHumanPlayerMove()
                    + "        "
                    + round.getComputerPlayerName()
                    + ": "
                    + round.getComputerPlayerMove()
                    + "   "
                    + "MATCH SCORE : "
                    + round.getHumanPlayerName().toUpperCase()
                    + ": "
                    + round.getHumanPlayerScore()
                    + "       "
                    + round.getComputerPlayerName().toUpperCase()
                    + ": "
                    + round.getComputerPlayerScore()));
    System.out.println("\n");
  }

  private void displayGameResult(String MatchWinner) {
    System.out.println("....GAME OVER...");
    System.out.println(MatchWinner + " is the winner!");
  }
}
