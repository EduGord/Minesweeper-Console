package src.model;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Game {
    private Board gameBoard = new Board(8,8);
    private Scanner sc = new Scanner(System.in);
    private String coordinateChoice;
    private int optionChoice;
    private BoardField chosenField;
    private String status = "playing";

    public void play(){
        gameBoard.displayBoard();
        // Game Loop
        while (status.equals("playing")) {
            displayMenu();
            if (optionChoice == 1)
                chosenField.open();
            else
                chosenField.toggleFlag();
            clearScreen();
            gameBoard.displayBoard();
            updateGameStatus();
        }
        sc.close();
    }

    public void displayMenu(){
        String pattern = "[0-9]?".repeat(gameBoard.getLines()/10 + 1) + "," + "[0-9]?".repeat(gameBoard.getColumns()/10 + 1);
        // Menu 1
        do {
            System.out.printf("%n1: Open     2: Flag%n");
            while (!sc.hasNextInt()) {
                System.out.printf("%n1: Open     2: Flag%n");
                System.out.println("That's not a valid input");
                sc.next();
            }
            optionChoice = sc.nextInt();
            if (!Arrays.asList(1, 2).contains(optionChoice))
                System.out.println("That's not a valid option");
        } while (!Arrays.asList(1, 2).contains(optionChoice));

        // Menu 2
        do {
            System.out.printf("%nType the coordinate x, y: ");
            while (!sc.hasNext(pattern)) {
                System.out.println("That's not a valid input");
                System.out.printf("%nType the coordinate x, y: ");
                sc.next();
            }
            coordinateChoice = sc.next();
            chosenField = gameBoard.getFieldByCoordinates(Integer.parseInt(Arrays.asList(coordinateChoice.split(",")).get(0)),
                                                      Integer.parseInt(Arrays.asList(coordinateChoice.split(",")).get(1)));
            if (chosenField == null) {
                System.out.println("There's no field associated with this coordenates.");
            }
        } while (chosenField == null);
    }

    public void updateGameStatus() {
        if (this.hasWon()) {
            this.status = "won";
            System.out.println("Congratulations, you've won !!");
        } else if (this.hasLost()) {
            this.status = "lost";
            System.out.println("Busted !!!");
        } else {
            this.status = "playing";
        }
    }

    public boolean hasLost() {
        return this.gameBoard.getFields().stream().anyMatch(f -> f.isMined() && f.isOpen());
    }

    public boolean hasWon() {
        List<BoardField> minedFields = this.gameBoard.getFields().stream().filter(f -> f.isMined()).collect(Collectors.toList());
        List<BoardField> nonMinedFields = this.gameBoard.getFields().stream().filter(f -> !f.isMined()).collect(Collectors.toList());

        if (!this.hasLost() &&
        minedFields.stream().allMatch(f-> f.isFlagged()) &&
        nonMinedFields.stream().allMatch(f -> f.isOpen())) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}



