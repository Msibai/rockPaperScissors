package org.example;

import java.util.ArrayList;
import java.util.List;
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

public class RPSgameLogic {
  private static int selectedComputerPlayerType;
  private final GameStatistics statistics;
  private final List<RoundHistory> roundHistoryList = new ArrayList<>();
  private HumanPlayer humanPlayer;
  private Player computerPlayer;
  private int humanScore;
  private int computerScore;

  public RPSgameLogic(GameStatistics statistics, int selectedComputerPlayerType) {
    this.statistics = statistics;
    RPSgameLogic.selectedComputerPlayerType = selectedComputerPlayerType;
  }

  private static Player generateComputerPlayer(int selectedComputerPlayerType) {
    ComputerPlayerFactory factory = new ComputerPlayerFactory();
    return switch (selectedComputerPlayerType) {
      case 0 -> factory.createRandomComputerPlayer("Random Beast", new RandomMoveStrategy());
      case 1 -> factory.createTimeBasedComputerPlayer("Time Master", new TimeBasedMoveStrategy());
      case 2 -> factory.createNameBasedComputerPlayer(
          "Tactics Master", new NameBasedMoveStrategy());
      default -> factory.createRandomComputerPlayer("Random Beast", new RandomMoveStrategy());
    };
  }

  public void play(String playerName) {
    humanPlayer = new HumanPlayer(playerName, new HumanPlayerMoveStrategy());
    computerPlayer = generateComputerPlayer(selectedComputerPlayerType);

    int totalWinNeeded = getDesiredWinCount();

    while (!isMatchOver(totalWinNeeded)) {
      playOneRound();
      displayCurrentScores();
    }
    Player matchWinner = determineMatchWinner();
    String matchWinnerName = matchWinner.getName();
    displayMatchResult(matchWinnerName);
    statistics.updateStatistics(matchWinner, computerPlayer);
    displayRoundHistory();
  }

  private void playOneRound() {
    String computerMove = computerPlayer.makeMove(humanPlayer);
    String humanMove = humanPlayer.makeMove(computerPlayer);
    String resultForOneRound = determineRoundWinner(humanMove, computerMove);
    updateScore(resultForOneRound);
    roundHistoryList.add(
        new RoundHistory(
            humanPlayer.getName(),
            humanMove,
            computerPlayer.getName(),
            computerMove,
            humanScore,
            computerScore));

    System.out.println(humanPlayer.getName() + " played: " + humanMove);
    System.out.println(computerPlayer.getName() + " played: " + computerMove);
    System.out.println("Result: " + resultForOneRound + "\n");
  }

  private int getDesiredWinCount() {
    int desiredWinCount = 0;
    boolean validInput = false;

    while (!validInput) {
      System.out.print("Enter desired number of Wins to declare a Winner: ");
      Scanner scanner = new Scanner(System.in);

      if (scanner.hasNextInt()) {
        desiredWinCount = scanner.nextInt();
        if (desiredWinCount >= 0) {
          validInput = true;
        } else {
          System.out.println("Please enter a non-negative integer.");
        }
      } else {
        System.out.println("Please enter a valid integer.");
      }
    }
    return desiredWinCount;
  }

  private void displayCurrentScores() {
    System.out.println(
        "Current Score - "
            + humanPlayer.getName().toUpperCase()
            + ": "
            + humanScore
            + "     "
            + computerPlayer.getName().toUpperCase()
            + ": "
            + computerScore
            + "\n");
  }

  private void displayRoundHistory() {
    System.out.println("\n ROUND HISTORY");
    roundHistoryList.forEach(
        (round) -> {
          System.out.println(
              round.getHumanPlayerName()
                  + ": "
                  + round.getHumanPlayerMove()
                  + "        "
                  + round.getComputerPlayerName()
                  + ": "
                  + round.getComputerPlayerMove()
                  + "                  "
                  + "MATCH SCORE : "
                  + round.getHumanPlayerName().toUpperCase()
                  + ": "
                  + round.getHumanPlayerScore()
                  + "       "
                  + round.getComputerPlayerName().toUpperCase()
                  + ": "
                  + round.getComputerPlayerScore());
        });
    System.out.println("\n");
  }

  private void displayMatchResult(String MatchWinner) {
    System.out.println("....GAME OVER...");
    System.out.println(MatchWinner + " is the winner!");
  }

  public String determineRoundWinner(String humanMove, String computerMove) {
    if (humanMove.equals(computerMove)) {
      return "TIE";
    } else if ((humanMove.equals("rock") && computerMove.equals("scissors"))
        || (humanMove.equals("paper") && computerMove.equals("rock"))
        || (humanMove.equals("scissors") && computerMove.equals("paper"))) {
      System.out.println("You win!");
      return humanPlayer.getName();
    } else {
      System.out.println(computerPlayer.getName() + " wins!");
      return computerPlayer.getName();
    }
  }

  public void updateScore(String roundWinner) {
    if (roundWinner.equals(computerPlayer.getName())) {
      computerScore++;
    } else if (roundWinner.equals(humanPlayer.getName())) {
      humanScore++;
    }
  }

  public boolean isMatchOver(int totalWinNeeded) {
    return computerScore == totalWinNeeded || humanScore == totalWinNeeded;
  }

  public Player determineMatchWinner() {
    if (humanScore > computerScore) {
      return humanPlayer;
    } else {
      return computerPlayer;
    }
  }
}
