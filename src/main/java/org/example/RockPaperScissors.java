package org.example;

import org.example.menu.MenuSystem;

public class RockPaperScissors {

  public static void main(String[] args) {

    System.out.println("\n \n........ROCK PAPER SCISSORS........ \n \n");

    MenuSystem menuSystem = MenuSystem.getInstance();
    while (true) {
      menuSystem.execute();
    }
  }
}
