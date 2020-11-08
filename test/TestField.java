package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.model.BoardField;

public class TestField {

    private BoardField aField;

    @BeforeEach
    void initField() {
        aField = new BoardField(3,3);
    }

    @Test
    void testNeighbors(){
        List<BoardField> neighbors =
            Arrays.asList(
                new BoardField(2,2),
                new BoardField(2,3),
                new BoardField(2,4),
                new BoardField(3,2),
                new BoardField(3,4),
                new BoardField(4,2),
                new BoardField(4,3),
                new BoardField(4,4)
        );

        // Test #1
        for (BoardField boardField : neighbors) {
            assertTrue(String.format("(%d, %d)%n", boardField.getLine(), boardField.getColumn()),
                       boardField.isNeighbor(aField));
        }

        List<BoardField> allFields = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                allFields.add(new BoardField(i,j));
            }
        }

        List<BoardField> notNeighbors =
            allFields.stream()
                     .filter(f -> neighbors.stream().allMatch(f2 -> !f.equals(f2)))
                     .collect(Collectors.toList());

        // Test #2
        for (BoardField boardField : notNeighbors) {
            assertTrue(String.format("(%d, %d)%n", boardField.getLine(), boardField.getColumn()),
                       !boardField.isNeighbor(aField));
        }

        // Test #1 2x
        boolean neighborScenario = neighbors.stream().allMatch(f -> (aField.isNeighbor(f)));
        assertTrue(neighborScenario);

        // Test #2 2x
        boolean notNeighborScenario = notNeighbors.stream().allMatch(f -> (!aField.isNeighbor(f)));
        assertTrue(notNeighborScenario);
    }
}
