package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BoardField {
    // Basic Info
    private int line;
    private int column;
    // Status
    private boolean open = false;
    private boolean closed = true;
    private boolean flagged = false;
    private boolean mined = false;
    // Miscellaneous
    private List<BoardField> neighbors = new ArrayList<>();

    // Constructors
    public BoardField(){}
    public BoardField(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void open() {
        if (this.closed) {
            this.closed = false;
            this.open = true;
            if (!this.mined) {
                for (BoardField boardField : neighbors) {
                    if (!boardField.isMined()){
                        if (boardField.neighbors.stream().allMatch(f -> !f.isMined()))
                            boardField.open();
                        else
                            boardField.setOpen();
                    }
                }
            }
        }
    }

    private String countMinedNeighbors(){
        return String.valueOf(this.neighbors.stream().filter(aField -> aField.isMined()).count());
    }

    public boolean isNeighbor(BoardField boardField){
        if (Math.abs(this.line - boardField.line) <= 1 &&
           (Math.abs(this.column - boardField.column) <= 1) &&
           (!boardField.equals(this))){
               return true;
           } else {
               return false;
           }
    }

    @Override
    public String toString(){
        String content = "[ ]";
        if (this.open && this.mined){
            content = " [\uD83D\uDCA3]";
            // content = "*";
        }
        if (this.open && !this.mined ) {
            content = String.format(" [%s] ",this.countMinedNeighbors().toString());
        }
        if (this.closed && this.flagged) {
            content = " [F] ";
        }
        if (this.closed && !this.flagged){
            content = " [ ] ";
        }
        return content;
    }

    // Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoardField)) {
            return false;
        }
        BoardField boardField = (BoardField) o;
        return line == boardField.line && column == boardField.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column, open, closed, flagged, mined, neighbors);
    }

    // Getters & Setters
    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen() {
        if (this.closed) {
            this.closed = false;
            this.open = true;
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public void toggleFlag() {
        this.flagged = !this.flagged;
    }

    public boolean isMined() {
        return this.mined;
    }

    public boolean setMined() {
        if (!this.mined) {
            this.mined = true;
            return true;
        }
        else
            return false;
    }

    public List<BoardField> getNeighbors() {
        return this.neighbors;
    }

    public void setNeighbors(List<BoardField> neighbors) {
        this.neighbors = neighbors;
    }


}
