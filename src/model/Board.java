package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Board {
    private final int lines;
    private final int columns;
    private List<BoardField> fields = new ArrayList<>();
    private final int NMINES;

    // Constructor
    public Board(){
        this.lines = 4;
        this.columns = 4;
        this.NMINES = 4;
        this.init();
    }
    public Board(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.NMINES = (int) (lines + columns)/2 ;
        this.init();
    }

    // Methods
    public void displayBoard(){
        BoardField aField;
        System.out.print("  ");
        for (int i = 0; i < this.lines; i++) {
            System.out.printf("  %d  ",i);
        }
        System.out.println();
        for (int i = 0; i < this.lines; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (j==0) System.out.printf("%d ", i);
                aField = getFieldByCoordinates(i, j);
                System.out.print(aField);
            }
            System.out.println();
        }
    }

    public BoardField getFieldByCoordinates(int x, int y) {
        if (x < this.lines && y < this.columns)
            return this.fields.stream().filter(f -> f.getLine() == x).filter(f -> f.getColumn() == y).findFirst().get();
        else
            return null;
    }

    private void generateFields(){
        for (int i = 0; i < this.lines; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.fields.add(new BoardField(i, j));
            }
        }
    }

    private void generateMines(){
        Random randomGenerator = new Random();
        for (int i = 0; i < NMINES; i++) {
            while (!this.fields.get(randomGenerator.nextInt(fields.size())).setMined());
        }
    }

    private void populateNeighbors() {
        for (BoardField boardField : this.fields) {
            boardField.setNeighbors(this.fields.stream().filter(f -> Math.abs(f.getLine() - boardField.getLine()) <= 1)
                                                        .filter(f -> Math.abs(f.getColumn() - boardField.getColumn()) <= 1)
                                                        .filter(f -> !f.equals(boardField)).collect(Collectors.toList()));
        }
    }

    public void init() {
        this.generateFields();
        this.generateMines();
        this.populateNeighbors();
    }

    // Getters && Setters
    public int getLines() {
        return this.lines;
    }

    public int getColumns() {
        return this.columns;
    }

    public List<BoardField> getFields() {
        return this.fields;
    }

    public void setFields(List<BoardField> fields) {
        this.fields = fields;
    }

    public int getNMines() {
        return this.NMINES;
    }

    public List<BoardField> getMinedFields() {
        return this.fields.stream().filter(f -> f.isMined()).collect(Collectors.toList());
    }

    public List<BoardField> getNonMinedFields() {
        return this.fields.stream().filter(f -> !f.isMined()).collect(Collectors.toList());
    }


}
