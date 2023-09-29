package org.example.Menu;

import java.util.List;
import org.example.RPSgameLogic;

public class GameMenu extends Menu {

    public GameMenu(String playerName) {
        super("Game Menu");
        System.out.println("Hello, " + playerName + " ! Choose Your Opponent ");
        menuOptions =
                List.of(
                        new MenuOption(
                                1,
                                " Random Move Maker",
                                () -> {
                                    RPSgameLogic game = new RPSgameLogic(0);
                                    game.play(playerName);
                                }),
                        new MenuOption(
                                2,
                                " Time Based Move Maker",
                                () -> {
                                    RPSgameLogic game = new RPSgameLogic(1);
                                    game.play(playerName);
                                }),
                        new MenuOption(
                                3,
                                " Name Based Move Maker",
                                () -> {
                                    RPSgameLogic game = new RPSgameLogic(2);
                                    game.play(playerName);
                                }),
                        new MenuOption(4, "Go back to Main Menu ", () -> MenuSystem.setState(new MainMenu())));
    }
}