package org.example.Menu;

import java.util.List;
import java.util.Scanner;

public class Menu implements MenuState{

    protected String menuName;
    protected List<MenuOption> menuOptions;

    public Menu(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public void execute() {
        displayMenuOptions();
        boolean validInput = false;
        while(!validInput){
         Scanner input = new Scanner(System.in);
         if (input.hasNextInt()) {
         int optionSelected = input.nextInt();
         if(optionSelected > 5 || optionSelected <= 0){
             System.out.println("Please chose valid option number between 1 to 4:");}
         else{
             validInput = true;
         for(MenuOption menuOption: menuOptions){
             if(menuOption.getOptionNumber() == optionSelected){
                 menuOption.run();
             }}}}
         else{
                 System.out.println("Please enter a valid integer");
             }
         }

    }

    private void displayMenuOptions() {
        String menuText = menuName;
        for(MenuOption menuOption: menuOptions){
            menuText = menuText.concat(
                    "\n"+ menuOption.getOptionNumber() +
                            ".) " + menuOption.getText()
            );
        }
        System.out.println(menuText);
    }
}
