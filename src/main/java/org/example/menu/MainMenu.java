package org.example.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.example.game.RPSgame;
import org.example.moveStrategy.HumanPlayerMoveStrategy;
import org.example.player.HumanPlayer;
import org.example.player.Player;
import org.example.statistics.GameStatistics;
import org.example.tournament.Tournament;

public class MainMenu extends Menu {

  private static final Scanner scanner = new Scanner(System.in);
  private static final GameStatistics statistics = new GameStatistics();

  private static String playerName = "";

  public MainMenu() {
    super("Main Menu");
    menuOptions =
        List.of(
            new MenuOption(1, "Start Single Game", this::startGame),
            new MenuOption(2, "Start Single-Elimination Tournament", this::startTournament),
            new MenuOption(3, "See Game statistics", statistics::displayStatistics),
            new MenuOption(4, "See Game Rules", this::gameRules),
            new MenuOption(5, "SAYONARA!! ", () -> System.exit(0)));
  }

  private void gameRules() {
    System.out.println("***********************************************");
    System.out.println(" 1. Rock wins against scissors.");
    System.out.println(" 2. Scissors wins against paper.");
    System.out.println(" 3. Paper wins against rock.");
    System.out.println("*********************************************** \n");
    System.out.println(
        " Note:  If both players throw the same hand signal, it is considered a tie, and play resumes until there is a clear winner.\n");
  }

  private void collectPlayerName() {
    while (playerName.isEmpty()) {
      System.out.println("Enter Your Name: ");
      playerName = scanner.nextLine();
    }
  }

  public void startGame() {
    collectPlayerName();
    MenuSystem.setState(new GameMenu(playerName, statistics));
  }

  public void startTournament() {
    collectPlayerName();
    int totalWinNeeded = RPSgame.getDesiredWinCount();
    int participantNumber = getParticipantNumber();

    List<Player> participants = createParticipantList(participantNumber);

    RPSgame game = new RPSgame(totalWinNeeded, statistics);
    Tournament tournament = new Tournament(game, participants);

    Player winner = tournament.startTournament();

    announceWinner(winner);
  }

  private int getParticipantNumber() {
    int participants = 0;
    boolean validInput = false;

    while (!validInput) {
      System.out.print("Please choose the number of participants: ");

      try {
        participants = Integer.parseInt(scanner.nextLine());

        if (participants <= 0) {
          System.out.println("Please enter a positive integer greater than zero.");
        } else {
          validInput = true;
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid integer.");
      }
    }

    return participants;
  }

  private List<Player> createParticipantList(int participantNumber) {
    List<Player> participants = new ArrayList<>(participantNumber);
    participants.add(new HumanPlayer(playerName, new HumanPlayerMoveStrategy()));

    for (int i = 0; i < participantNumber - 1; i++) {
      participants.add(RPSgame.generateComputerPlayer(new Random().nextInt(3)));
    }
    return participants;
  }

  private void announceWinner(Player winner) {
    System.out.println("The winner is " + winner.getName());
  }
}
