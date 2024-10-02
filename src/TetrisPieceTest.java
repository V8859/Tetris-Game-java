import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TetrisPieceTest {

    @Test
    public void testRotate() {
        // Create a new Tetris piece (assuming the constructor sets the initial shape)
        int[][][] shape = {
                {
                        {1, 1, 0},
                        {0, 1, 1},
                        {0, 0, 0}
                },
                {
                        {0, 1, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                }
        };
        TetrisPiece piece = new TetrisPiece(shape, Color.BLUE);

        // Store the initial shape
        int[][] initialShape = piece.getCurrentShape();

        // Rotate the piece
        piece.rotate();

        // Ensure the shape changes after rotation
        int[][] rotatedShape = piece.getCurrentShape();
        assertNotEquals(initialShape, rotatedShape, "Shape should change after rotation.");

        // Rotate again to ensure it cycles through correctly
        piece.rotate();
        assertNotEquals(rotatedShape, piece.getCurrentShape(), "Shape should change again after a second rotation.");

        // Optionally check if after multiple rotations it returns to the original shape
        piece.rotate();
        piece.rotate();
        assertArrayEquals(initialShape, piece.getCurrentShape(), "After four rotations, the shape should return to the initial state.");
    }
}
