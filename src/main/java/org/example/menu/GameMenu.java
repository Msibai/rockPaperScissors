package org.example.menu;

import java.util.List;
import org.example.game.RPSgame;
import org.example.statistics.GameStatistics;

public class GameMenu extends Menu {

  public GameMenu(String playerName, GameStatistics statistics) {
    super("Game Menu");
    System.out.println("Hello, " + playerName + " ! Choose Your Opponent ");
    menuOptions =
        List.of(
            new MenuOption(
                1,
                " Random Move Maker",
                () -> {
                  RPSgame game = new RPSgame(statistics, 0);
                  game.startSingleGame(playerName);
                }),
            new MenuOption(
                2,
                " Time Based Move Maker",
                () -> {
                  RPSgame game = new RPSgame(statistics, 1);
                  game.startSingleGame(playerName);
                }),
            new MenuOption(
                3,
                " Name Based Move Maker",
                () -> {
                  RPSgame game = new RPSgame(statistics, 2);
                  game.startSingleGame(playerName);
                }),
            new MenuOption(4, "Go back to Main Menu ", () -> MenuSystem.setState(new MainMenu())));
  }
}
