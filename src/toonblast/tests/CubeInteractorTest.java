package toonblast.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import toonblast.Grid;
import toonblast.element.Cube;
import toonblast.element.Element;
import toonblast.interaction.CubeInteractor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CubeInteractorTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCubeInteractor() {
        Element[][] elements = new Element[][]{
                {new Cube(1), new Cube(1)},
                {new Cube(1), new Cube(2)}
        };
        var sampleGrid = new Grid(elements, null);

        var interactor = new CubeInteractor(sampleGrid);
        var neighbors = interactor.interact(elements[0][1]);

        var expectedNeighbors = Arrays.asList(
                elements[0][0], elements[0][1], elements[1][0]
        );

        assertEquals(expectedNeighbors, neighbors);
    }
}