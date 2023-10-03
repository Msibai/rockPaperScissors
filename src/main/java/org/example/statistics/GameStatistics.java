package org.example.statistics;

import org.example.player.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GameStatistics {

  private static final Map<String, ComputerPlayerStatistics> statisticsByPlayerType =
      new HashMap<>();
  private static int totalMatches;
  private static int userMatchesWon;

  public void updateStatistics(Player matchWinner, Player computerPlayer) {
    totalMatches++;
    if (matchWinner instanceof HumanPlayer) {
      userMatchesWon++;
    }

    String computerPlayerType = getComputerPlayerType(computerPlayer);
    ComputerPlayerStatistics playerStatistics =
        statisticsByPlayerType.getOrDefault(computerPlayerType, new ComputerPlayerStatistics());
    playerStatistics.update(matchWinner instanceof HumanPlayer);
    statisticsByPlayerType.put(computerPlayerType, playerStatistics);
  }

  public static void displayStatistics() {
    double userWinPercentage = (double) userMatchesWon / totalMatches * 100;
    double computerWinPercentage = 100 - userWinPercentage;

    if (totalMatches == 0) {
      userWinPercentage = 0;
      computerWinPercentage = 0;
    }

    System.out.println("Your Win Percentage: " + userWinPercentage + "%");
    System.out.println("Computer's Win Percentage: " + computerWinPercentage + "%");

    displayWinPercentagesByComputerType();
  }

  public static void displayWinPercentagesByComputerType() {
    System.out.println("TOTAL MATCHES: " + totalMatches);
    System.out.println("TOTAL MATCHES YOU WON: " + userMatchesWon);
    System.out.println("Your Win Percentages by Computer Player Type:");

    Map<String, Double> winPercentagesByPlayerType = statisticsByPlayerType.entrySet()
            .stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().getWinPercentage()
            ));

    winPercentagesByPlayerType.forEach((type, percentage) ->
            System.out.println(type + ": " + percentage + "%")
    );
  }

  private String getComputerPlayerType(Player computerPlayer) {
    if (computerPlayer instanceof RandomComputerPlayer) {
      return "Random";
    } else if (computerPlayer instanceof TimeBasedComputerPlayer) {
      return "Time-Based";
    } else if (computerPlayer instanceof NameBasedComputerPlayer) {
      return "Name-Based";
    }
    return "Unknown";
  }

}
