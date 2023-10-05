package org.example;

import java.util.List;
import org.example.tournament.Tournament;
import org.example.moveStrategy.RandomMoveStrategy;
import org.example.player.Player;
import org.example.player.RandomComputerPlayer;

public class RockPaperScissors {

  public static void main(String[] args) {

    List<Player> participant =
        List.of(
            new RandomComputerPlayer("Random2", new RandomMoveStrategy()),
            new RandomComputerPlayer("Random1", new RandomMoveStrategy()),
            new RandomComputerPlayer("Random3", new RandomMoveStrategy()),
            new RandomComputerPlayer("Random4", new RandomMoveStrategy()),
            new RandomComputerPlayer("Random5", new RandomMoveStrategy()),
            new RandomComputerPlayer("Random6", new RandomMoveStrategy()),
            new RandomComputerPlayer("Random7", new RandomMoveStrategy()),
            new RandomComputerPlayer("Random8", new RandomMoveStrategy()));

    Tournament tournament = new Tournament(participant);
    Player winner = tournament.startTournament();
    System.out.println("The winner is " + winner.getName());

    //    System.out.println("\n \n........ROCK PAPER SCISSORS........ \n \n");
    //
    //    MenuSystem menuSystem = MenuSystem.getInstance();
    //    while (true) {
    //      menuSystem.execute();
    //    }
  }
}
